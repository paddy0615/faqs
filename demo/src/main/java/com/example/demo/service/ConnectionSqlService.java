package com.example.demo.service;

import com.example.demo.bean.E_form_result;
import com.example.demo.entity.CommomClass;
import com.example.demo.util.ConnectionSqlUtil;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("connectionSqlService")
public class ConnectionSqlService {

    public static String getJobID(String pnr) {
        String sql = "select ordersID from flightirr_sendingreps_logs where pnr='" + pnr + "' group by ordersID ";

        PreparedStatement pre = null;
        ResultSet result = null;
        String ordersID = "";
        try {
            Connection con = ConnectionSqlUtil.getConnectionIRR();
            pre = con.prepareStatement(sql);
            result = (ResultSet)pre.executeQuery();
            while (result.next())
            {
                ordersID = ordersID + "'" + result.getString("ordersID") + "'" + ",";
            }
            con.close();
            pre.close();
            result.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ordersID;
    }

    public static List searchFlightIRRList(String pnr, String fltNo, String monitoringDate1, E_form_result result){
        String result_txt = "irr不存在";
        List irrList = new ArrayList();
        String ordersID = "";
        String  sql = "";
        try {
            if ((pnr != null) && (pnr.length() != 0)) {
                ordersID = getJobID(pnr);
                if ((ordersID != null) && (ordersID.length() != 0)) {
                    ordersID = ordersID.substring(0, ordersID.length() - 1);
                    sql = "select jobID,irrCategory,template,reasons,flightNo,newFlightNo,departingFrom,arrivingAt," +
                            "departureDate,arrivalDate,newDepartureDate,newArrivalDate,createdDate,protectionoffer " +
                            " from flightirr_sendingorders where ID in (" + ordersID + ") and status=1 and flightNo='UO" + fltNo + "'" +
                            " ORDER BY createdDate DESC LIMIT 1";
                    irrList = getList(sql);
                    result_txt = "irr存在"+irrList.size()+"pnr="+pnr;
                    System.out.println(result_txt);
                }
            }

           /* if(irrList.size()==0){
                if ((fltNo != null) && (fltNo.length() != 0) && (monitoringDate1 != null) && (monitoringDate1.length() != 0)) {
                    String d1 = monitoringDate1 + " 00:00";
                    String d2 = monitoringDate1 + " 23:59";
                    sql = "select jobID,irrCategory,irrNature as template,reasons,flightNo,newFlightNo,departingFrom,arrivingAt,departureDate," +
                            "arrivalDate,newDepartureDate,newArrivalDate,match_Time as createdDate " +
                            " from flightirr_dc_paired where flightNo='UO" + fltNo + "' and departureDate between '" + d1 + "' and '" + d2 + "' and status=1 " +
                            " ORDER BY match_Time DESC LIMIT 1";
                    irrList = getList(sql);
                    result_txt = "irr存在"+irrList.size()+"fltNo="+fltNo+"&monitoringDate1="+monitoringDate1;
                    System.out.println(result_txt);
                }
            }*/
            result.setResultxml(result.getResultxml()+","+result_txt);
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return irrList;
    }


    public static List getList( String sql) {
        List reaList = new ArrayList();
        List irrList = new ArrayList();
        PreparedStatement pre = null;
        ResultSet result = null;
        try {
            List reasonList = getIRR_Reason();
            if (reasonList != null) {
                for (int i = 0; reasonList.size() > i; i++) {
                    CommomClass l = (CommomClass)reasonList.get(i);
                    reaList.add(l);
                }
            }

            Connection con = ConnectionSqlUtil.getConnectionIRR();
            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            while (result.next()) {
                CommomClass cc = new CommomClass();

                String reasonStr = "";
                int r_id = Integer.parseInt(result.getString("reasons"));
                if (reaList != null) {
                    for (int r = 0; reaList.size() > r; r++) {
                        CommomClass c = (CommomClass)reaList.get(r);
                        if (c.getR_id() == r_id) {
                            reasonStr = c.getReason_TC() + "/" +c.getReason_EN() ;
                        }
                    }
                }
                String departure_dt = result.getString("departureDate");
                String arrival_dt = result.getString("arrivalDate");
                String departure_dt_new = result.getString("newDepartureDate");
                String arrival_dt_new = result.getString("newArrivalDate");
                if ((departure_dt != null) && (departure_dt.length() != 0)) {
                    String[] dd = departure_dt.split(" ");
                    cc.setDepartureDate(dd[0]);
                    cc.setDepartureTime(dd[1]);
                }
                if ((arrival_dt != null) && (arrival_dt.length() != 0)) {
                    String[] ad = arrival_dt.split(" ");
                    cc.setArrivalDate(ad[0]);
                    cc.setArrivalTime(ad[1]);
                }
                if ((departure_dt_new != null) && (departure_dt_new.length() != 0)) {
                    String[] ndd = departure_dt_new.split(" ");
                    cc.setNewDepartureDate(ndd[0]);
                    cc.setNewDepartureTime(ndd[1]);
                }
                if ((arrival_dt_new != null) && (arrival_dt_new.length() != 0)) {
                    String[] nad = arrival_dt_new.split(" ");
                    cc.setNewArrivalDate(nad[0]);
                    cc.setNewArrivalTime(nad[1]);
                }
                cc.setJobID(result.getString("jobID"));
                cc.setIrrCategory(result.getString("irrCategory"));
                cc.setTemplate(result.getString("template"));
                cc.setReasons(reasonStr);
                cc.setFlightNo(result.getString("flightNo"));
                cc.setNewFlightNo(result.getString("newFlightNo"));
                cc.setDepartingFrom(result.getString("departingFrom"));
                cc.setArrivingAt(result.getString("arrivingAt"));
                cc.setCreatedDate(result.getString("createdDate"));
                irrList.add(cc);
            }

            con.close();
            pre.close();
            result.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return irrList;
    }


    public static List getIRR_Reason() {
        String sql = "select id,reason_EN,reason_TC,reason_SC,reason_JP,reason_KR from flightirr_disruptionreasons";

        PreparedStatement pre = null;
        ResultSet result = null;
        List list = new ArrayList();
        try {
            Connection con = ConnectionSqlUtil.getConnectionIRR();
            pre = con.prepareStatement(sql);
            result = (ResultSet)pre.executeQuery();
            while (result.next()) {
                CommomClass cc = new CommomClass();
                cc.setR_id(Integer.parseInt(result.getString("id")));
                cc.setReason_EN(result.getString("reason_EN"));
                cc.setReason_TC(result.getString("reason_TC"));
                cc.setReason_SC(result.getString("reason_SC"));
                cc.setReason_JP(result.getString("reason_JP"));
                cc.setReason_KR(result.getString("reason_KR"));
                list.add(cc);
            }
            con.close();
            pre.close();
            result.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
