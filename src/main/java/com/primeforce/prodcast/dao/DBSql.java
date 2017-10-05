package com.primeforce.prodcast.dao;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class DBSql {
    public final static String LOGIN_SQL_NEW = "select emp.employee_id , emp.firstname , emp.lastname , emp.title , emp.email_id , emp.cellphone , emp.homephone, emp.workphone, emp.salary, emp.hire_dt, emp.termination_dt,emp.allowance, emp.address_1 , emp.address_2 , emp.address_3 , emp.city, emp.state , emp.postal_code, emp.country_id , emp.active_yn , emp.type , emp.location , emp.sex , emp.dob, emp.comments , emp.user_id , uaccess.email_id , uaccess.password, uaccess.user_role, uaccess.user_id,dst.cust_name,dst.open_to_public , dst.timezone, ctry.country_id ,ctry.currency_symbol ,ctry.isd_code from employees emp , user_access uaccess,dist_dtl dst,country ctry where  dst.dist_id = emp.dist_manf_id and emp.employee_id = uaccess.employee_id and emp.email_id = ? and uaccess.password = ? and emp.country_id=ctry.country_id and dst.active_yn='1' and emp.active_yn='1' ";
    public final static String GET_DISTRIBUTORS_FOR_CUSTOMERS = "select dst.*,ctry.currency_symbol from dist_dtl dst,country ctry,outlet_dtl outlet,customer_access cust where  cust.access_id=? and cust.username=outlet.cellphone and outlet.country_id=cust.country_id and outlet.country_id=ctry.country_id and dst.active_yn='1' and outlet.active_yn='1' and outlet.dist_id=dst.dist_id";
    public final static String GET_EMPLOYEE_FOR_CUSTOMER_ORDER = "select emp.employee_id,outlet.*,user.user_role from employee_areamap areamap,outlet_dtl outlet,customer_access cust,employees emp,user_access user where cust.access_id=? and cust.username=outlet.cellphone and cust.country_id=outlet.country_id and outlet.dist_id=emp.dist_manf_id and emp.employee_id=areamap.employee_id and outlet.area=areamap.area_id and outlet.dist_id=? and emp.employee_id=user.employee_id";
    public final static String GET_DISTRIBUTOR_FOR_CUSTOMER_ORDER = "select emp.employee_id,outlet.*,user.user_role from employee_areamap areamap,outlet_dtl outlet,customer_access cust,employees emp,user_access user where cust.access_id=? and cust.username=outlet.cellphone and cust.country_id=outlet.country_id and outlet.dist_id=emp.dist_manf_id and emp.employee_id=areamap.employee_id and outlet.dist_id=? and emp.employee_id=user.employee_id and user.user_role='D'";
    public final static String CUSTOMER_SEARCH_SQL = "select * from outlet_dtl where area in ( select area_id from employee_areamap where employee_id = ? ) and active_yn='1'  order by outlet_name";
    public final static String CUSTOMER_SEARCH_SQL_D = "select * from outlet_dtl where area in ( select area_id from area_dtl where dist_id = (select dist_manf_id from employees where employee_id =? ) ) and active_yn='1' order by outlet_name";
    public final static String CUSTOMER_SEARCH_SQL_PUBLIC = "select distinct od.* from outlet_dtl od ,order_header oh , dist_dtl dt , employees emp where emp.dist_manf_id = dt.dist_id and dt.dist_id = oh.distributor_id and oh.cust_id = od.outlet_id and emp.employee_id = ?";
    public final static String CUSTOMERS_OUTSTANDING_BILL_SEARCH = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname,oh.order_status from  order_header oh,outlet_dtl cust where oh.outstanding_balance >0 and oh.order_status <> 'D' and oh.cust_id=cust.outlet_id and oh.distributor_id = ( select dist_manf_id from employees where employee_id = ? ) ";
    //public final static String CUSTOMERS_OUTSTANDING_BILL_SEARCH_PUBLIC = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname,oh.order_status from  order_header oh,outlet_dtl cust where oh.outstanding_balance >0 and oh.order_status <> 'D' and oh.cust_id=cust.outlet_id";

    public final static String COUNTRY_SQL = "select * from country";
    public final static String COUNTRY_FOR_TIMEZONE = "select * from timezones";
    public final static String GET_EMP_ROLE = "select user_role from user_access where employee_id = ?";
    public final static String GET_EMP_DIST_PUBLIC = "select open_to_public from dist_dtl dist ,employees emp  where emp.dist_manf_id = dist.dist_id and employee_id = ?";
    public final static String CUSTOMER_SQL = "select *  from outlet_dtl where outlet_id=? and active_yn='1' ";
    public final static String CUSTOMER_FROM_BILL_SQL = "select *  from outlet_dtl where outlet_id=(select cust_id from order_header where  bill_no=?) and active_yn='1'";

    public final static String PRODUCT_SEARCH_SQL = " select * from products where dist_id = (select dist_manf_id from employees where employee_id = ? ) and active='1' order by product_name";
    public final static String OUTSTANDING_BILL_SEARCH = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname,oh.order_status from  order_header oh where oh.cust_id = ? and oh.outstanding_balance >0 and oh.order_status <> 'D'";
    public final static String OUTSTANDING_BILL_SEARCH_CUST = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname,oh.order_status from  order_header oh where oh.cust_id = ? and oh.outstanding_balance >0 and oh.order_status <> 'D' and oh.distributor_id=(select dist_manf_id from employees where employee_id=?)";
    public final static String OUTSTANDING_BILL_SEARCH_PUBLIC = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname,oh.order_status from  order_header oh where oh.cust_id = ? and oh.outstanding_balance >0 and oh.order_status <> 'D' and oh.distributor_id = (select dist_manf_id from employees where employee_id = ? )";

    public final static String COLLECTION_UPDATE_SQL = "call sp_collection_update (? , ? , ?, ? , ? , ? , ?) ";
    public final static String EMP_CHANGE_PASSWORD = "update user_access set password = ? where employee_id = ? and password = ?";
    public final static String GET_EMPLOYEE_EMAIL = "select email_id from employees where employee_id = ?";
    public final static String GET_EMPLOYEE_EMAIL_FROM_EMAIL = "select password from user_access where email_id = ?";
    public final static String ORDER_DETAILS_SQL = "insert into order_dtl (orderdetailid, product_id , quantity, unitprice, amount,sales_tax,other_tax,subtotal) values( ? , ? , ?, " +
            "(select unitprice from products where product_id = ?) , " +
            "quantity*unitprice, " +
            "(select sales_tax from products where product_id = ?) , " +
            "(select other_tax from products where product_id = ?), " +
            "(amount+amount*((sales_tax+other_tax)/100))" +
            ")";
    public final static String ORDER_DETAILS_SQL_RETAILER = "insert into order_dtl (orderdetailid, product_id , quantity, unitprice, amount,sales_tax,other_tax,subtotal) values( ? , ? , ?, " +
            "(select retailprice from products where product_id = ?) , " +
            "quantity*unitprice, " +
            "(select sales_tax from products where product_id = ?) , " +
            "(select other_tax from products where product_id = ?), " +
            "(amount+amount*((sales_tax+other_tax)/100))" +
            ")";

    public final static String FETCH_ADMIN_DISTRIBUTORS = "select dst.*,ctry.currency_symbol from dist_dtl dst,country ctry where dist_id=0 and ctry.country_id=dst.country_id";
    // public final static String FETCH_ADMIN_DISTRIBUTORS_="select email_id from dist_dtl where dist_id=0";
    public final static String FETCH_ADMIN_DISTRIBUTORS_COUNTRY = "select isd_code from country where country_id=?";

    public final static String NEW_REGISTRATION = "insert into registration_details(firstname, lastname, country, email_id, cell_phone, comments,enter_dt_tm,updt_dt_tm) values(?, ?, ?, ?, ?,'',NOW(),NOW())";
    public final static String ORDER_TOTAL_SQL = "update order_header set total_amt =" +
            " (select sum( subtotal) from order_dtl where orderdetailid = ? )-" +
            "(select IFNULL(sum(rd.subtotal),0) from return_dtl rd where rd.orderdetailid = ?)," +
            " outstanding_balance = total_amt ," +
            " distributor_id=(select dist_manf_id from employees where employee_id = ?),shippingmethodid=?,delivery_address=? where orderdetailid = ? ";

    public final static String ORDER_UPDATE_TOTAL_SQL = "update order_header set outstanding_balance = (total_amt - (select IFNULL(sum(amount_paid),0) from collection_dtl where bill_no = ?)) where bill_no=?";

    public final static String ORDER_UPDATE_DISCOUNT_PERCENTAGE = "update order_header set discount=(total_amt*(?/100)), discount_type=2, total_amt = (total_amt-discount), outstanding_balance = (outstanding_balance-discount) where orderdetailid = ? ";
    public final static String ORDER_UPDATE_DISCOUNT_PERCENTAGE_NEW = "update order_header set discount=(total_amt*(?/100)), discount_type=2, total_amt = (total_amt*(1-?/100)), outstanding_balance = (outstanding_balance*(1-?/100)) where orderdetailid = ? ";
    public final static String GET_DISCOUNT_DETAILS = "select discount, discount_type,total_amt from order_header where orderdetailid = ? ";
    public final static String ORDER_UPDATE_DISCOUNT_VALUE = "update order_header set discount=?, discount_type=1, total_amt = (total_amt-discount),outstanding_balance = (outstanding_balance-discount) where orderdetailid = ? ";
    public final static String ORDER_UPDATE_DISCOUNT_VALUE_NEW = "update order_header set discount=?, discount_type=1, total_amt = (total_amt-?),outstanding_balance = (outstanding_balance-?) where orderdetailid = ? ";
    public final static String FETCH_ORDER_SQL = " select  dst.cust_name,cust.outlet_email_id , emp.email_id , dst.email_id as dist_email_id  , oh.bill_date," +
            " oh.distributor_id , oh.orderdetailid , oh.bill_no, oh.cust_id, cust.outlet_name, oh.emp_id , " +
            "emp.firstname , emp.lastname, oh.total_amt, oh.enter_dt_tm , oh.shippingmethodid,oh.delivery_address,oh.order_status,oh.outstanding_balance,oh.discount,oh.discount_type, " +
            "ctry.isd_code, cust.cellphone from order_header oh , employees emp , outlet_dtl cust ,dist_dtl dst,country ctry where " +
            "oh.cust_id = cust.outlet_id  and oh.emp_id = emp.employee_id and dst.dist_id = oh.distributor_id and oh.bill_no=? and ctry.country_id = cust.country_id and oh.order_status <> 'D' and  distributor_id=(select dist_manf_id from employees where employee_id = ?)";
    public final static String FETCH_ORDER_DTL_SQL = "select od.product_id , od.quantity , od.unitprice, od.amount , pr.product_name,od.sales_tax, od.other_tax, od.subtotal from order_dtl od, products pr where od.product_id = pr.product_id and od.orderdetailid = ?";
    public final static String FETCH_ORDER_COLLECTION_SQL = "select cd.bill_no, cd.amount_paid, cd.emp_id , emp.firstname , emp.lastname, cd.payment_type, cd.payment_date , cd.ref_no , cd.ref_detail , '' as outlet_name from collection_dtl cd , employees emp where emp.employee_id = cd.emp_id and bill_no=?";

    public final static String REPORT_SALES_SQL = "select oh.orderdetailid, oh.bill_no,oh.bill_date, oh.enter_dt_tm, oh.total_amt, oh.outstanding_balance, cust.outlet_name  from order_header oh , outlet_dtl cust where oh.cust_id = cust.outlet_id and oh.bill_date between ? and ? and oh.emp_id = ? and oh.order_status <> 'D'";
    public final static String REPORT_COLLECTION_SQL = "select cd.bill_no, cd.amount_paid , cd.emp_id , emp.firstname , emp.lastname , cd.payment_type , cd.payment_date, cust.outlet_name, cd.ref_no, cd.ref_detail from collection_dtl cd , outlet_dtl cust , employees emp where emp.employee_id = cd.emp_id and cd.cust_id = cust.outlet_id and cd.payment_date between ? and ? and cd.emp_id = ?";

    public final static String REPORT_SALES_SQL_DISTRIBUTOR = "select oh.orderdetailid, oh.bill_no,oh.bill_date, oh.enter_dt_tm, oh.total_amt, oh.outstanding_balance, cust.outlet_name  from  order_header oh , outlet_dtl cust where oh.cust_id = cust.outlet_id and oh.bill_date between ? and ? and oh.emp_id in  ( select emp_id from employees where dist_manf_id in ( select dist_manf_id from employees where employee_id = ?) )";
    public final static String REPORT_COLLECTION_SQL_DISTRIBUTOR = "select cd.bill_no, cd.amount_paid , cd.emp_id , emp.firstname , emp.lastname , cd.payment_type , cd.payment_date, cust.outlet_name,cd.ref_no, cd.ref_detail  from collection_dtl cd , outlet_dtl cust , employees emp where emp.employee_id = cd.emp_id and cd.cust_id = cust.outlet_id and cd.payment_date between ? and ? and cd.emp_id in ( select emp_id from employees where dist_manf_id in ( select dist_manf_id from employees where employee_id = ?) )";

    public final static String FETCH_RETURN_DTL_SQL = "select rd.product_id , rd.quantity , od.unitprice, rd.amount , pr.product_name ,od.sales_tax, od.other_tax, rd.subtotal from order_dtl od, return_dtl rd, products pr where  rd.orderdetailid = od.orderdetailid and rd.product_id = pr.product_id and od.orderdetailid = ?";

    public final static String FETCH_AREAS_SQL = "select * from area_dtl where area_id in ( select area_id from employee_areamap where employee_id = ?) order by area_desc";
    public final static String CREATE_CUSTOMER_SQL = "insert into outlet_dtl ( " +
            "dist_id , outlet_name , outlet_type , area, week_day," +
            "firstname ,lastname , outlet_email_id, cellphone, workphone, " +
            "landmark, address_1 , address_2 , address_3 , city , " +
            "state ,country_id , postal_code , comments  ,taxid_1," +
            "desc_1 ,taxid_2 ,desc_2,sms_mobile_Y_N, active_yn," +
            "enter_dt_tm , user_id,store_type_id, title, bus_id,  homephone," +
            "sex,notify_yn,business_date,ip_address,updt_dt_tm ) values (" +
            "(select dist_manf_id from employees where employee_id =?), ?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?,?,?," +
            "?,?,?,?,?,?,?,NOW(),?,?,'',0,'','', 1,?,'', NOW()) ";


    public final static String UPDATE_CUSTOMER_SQL = "update outlet_dtl set  " +
            "outlet_name=? , outlet_type=? , area=?, " +
            "week_day=?,firstname=?,lastname=? , " +
            "outlet_email_id=?, cellphone=? , workphone=? ," +
            " landmark=?,address_1=?,address_2 =?, " +
            "address_3 =?,city=? ,state=? ," +
            "country_id=? , postal_code=? ,taxid_1=?," +
            "desc_1=?,taxid_2=? ,desc_2=?," +
            "sms_mobile_Y_N=?,active_yn=?, user_id=? ,store_type_id=?," +
            "updt_dt_tm=NOW() where outlet_id=?";

    public final static String FETCH_NEW_REGISTRATION_COUNTRY = "select isd_code from country where country_id=?";
    public final static String CUSTOMERS_GET_CONFIRMATIONCODE_SQL = "select confirmation_number from customer_access where access_id=?";
    public final static String CUSTOMERS_DETAILS_GET_SQL = "select * from customer_access";
    public final static String CUSTOMER_SET_STATUS_SQL = "update customer_access set status='1',updt_dt_tm=NOW() where access_id=?";
    public final static String GET_STATUS_FOR_CUSTOMER = "select cust.*,ctry.isd_code from customer_access cust,country ctry where cust.username=? and cust.password=? and cust.country_id=? and ctry.country_id=cust.country_id";
    public final static String IS_CUSTOMER_REGISTERED_FOR_PUBLIC = "select count(od.outlet_id) from outlet_dtl od,customer_access access where od.dist_id = -1  and access.country_id = od.country_id and access.username = od.cellphone and access.access_id = ?";
    public final static String GET_LOGIN_FOR_CUSTOMER = "select cust.*,ctry.isd_code,ctry.currency_symbol from customer_access cust,country ctry where cust.access_id=? and ctry.country_id=cust.country_id";
    public final static String FETCH_ALL_MOBILE_NUMBERS = "select  country_id,username,access_id from customer_access where country_id=? and username=? ";
    public final static String CUSTOMER_REGISTRATION = "insert into customer_access (country_id,username,password,confirmation_number,enter_dt_tm,updt_dt_tm) values(" +
            "?,?,?,?,NOW(),NOW())";
    public final static String RESEND_CONFIRMATION_CODE = "update customer_access set confirmation_number=? where access_id=?";
    public final static String FETCH_MOBILE_NUMBER_TO_RESEND_CODE = "select username from customer_access where access_id=? ";
    public final static String FETCH_ISD_CODE = "select isd_code from country where country_id=? ";

    public final static String FETCH_COUNTRY = "select  country_id from customer_access where access_id=? ";

    public final static String CUS_CHANGE_PINNUMBER = "update customer_access set password = ? where access_id = ? and password = ? ";
    public final static String GET_CUSTOMER_PHONENUMBER = "select username from customer_access where access_id = ? ";
    public final static String GET_DISTRIBUTOR = "select dst.*,ctry.currency_symbol from dist_dtl dst,country ctry where dst.dist_id=? and ctry.country_id=dst.country_id";
    public final static String GET_CUSTOMER_TYPE = "select outlet_type from outlet_dtl where outlet_id=?";
    public final static String STORETYPE_SQL="select * from store_type where active_yn='1'";
    public final static String CREATE_STORE_TYPE_SQL="insert into store_type(store_type_name,active_yn,enter_dt_tm,updt_dt_tm) values (?,'1',NOW(),NOW())";
    public final static String UPDATE_STORE_TYPE_SQL="update store_type set store_type_name =? where store_type_id=?";
    public final static String DELETE_STORE_TYPE_SQL = "update store_type set active_yn='0' where store_type_id=?";
    public final static String  STORE_TYPE_ID_SQL = "select store_type_name,store_type_id,active_yn from store_type where store_type_id =?";
    public final static String GET_CUSTOMER_PHONENUMBER_FROM_PHONENUMBER = "select password from customer_access where username = ?";
    public final static String  CREATE_STORE_TYPE="insert into store_type(store_type_name,active_yn,enter_dt_tm,updt_dt_tm) values (?,'1',NOW(),NOW())";
    public final static String  FETCH__NEW_CUSTOMER_REG_DETAILS="select  firstname,lastname,outlet_email_id,workphone,cellphone,address_1,address_2,address_3,city,state,country_id,postal_code,sms_mobile_Y_N from outlet_dtl where dist_id=-1 and cellphone=?";
    public final static String  FETCH__UPDATED_NEWCUSTOMER_REG_DETAILS="select  firstname,lastname,outlet_email_id,workphone,cellphone,address_1,address_2,address_3,city,state,country_id,postal_code,sms_mobile_Y_N from outlet_dtl where outlet_id=? and cellphone=?";
    public final static String  FETCH_NEW_CUSTOMEREG_DETAILS="select od.outlet_id, od.firstname,od.lastname,od.outlet_email_id," +
            "od.workphone,od.address_1,od.address_2,od.address_3,od.city,od.state,od.country_id,od.postal_code," +
            "od.sms_mobile_Y_N,od.cellphone from outlet_dtl od,customer_access cust where od.dist_id=-1 and  od.cellphone=cust.username and cust.access_id=?";




    public final static String CREATE_NEW_CUSTOMER_SQL = "insert into outlet_dtl (firstname ," +
            "lastname , outlet_email_id, cellphone, workphone, address_1 , address_2 ," +
            " address_3 , city ,state ,country_id , postal_code ,  sms_mobile_Y_N,enter_dt_tm ,dist_id," +
            " title, bus_id,  homephone, sex,notify_yn, ip_address,updt_dt_tm, outlet_name , outlet_type , area, week_day,user_id,store_type_id,active_yn,landmark,comments,business_date) " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),-1,'',0,'','', 1,'', NOW(),CONCAT_WS(' ', firstname, lastname),'R','-1','','-1','-1','1','','',?) ";

    public final static String  UPDATE_STORE="update store_type set store_type_name =? where store_type_id=?";
    public final static String  FETCH_MOBILE_NUMBER_OF_CUSTOMERS="select outlet_id,outlet_name,landmark,address_1,address_2,address_3,city,country_id,postal_code,state,outlet_type,active_yn,firstname,lastname,workphone,cellphone,comments,outlet_email_id,week_day,area,taxid_1,desc_1,taxid_2,desc_2,sms_mobile_Y_N,store_type_id from outlet_dtl where country_id=? and cellphone=? ";

    public final static String UPDATE_NEW_CUSTOMER_REG_DETAILS_SQL="update outlet_dtl set firstname=?,lastname=?,outlet_email_id=?,workphone=?,address_1=?," +
            " address_2=?, address_3=?,city=?,state=?,country_id=?,postal_code=?,sms_mobile_Y_N=?,outlet_name=CONCAT_WS(' ',firstname,lastname) where  cellphone=? and outlet_id=?";
    //public final static String UPDATE_NEW_CUST_REG_OUTLETNAME_SQL="update outlet_dtl set outlet_name=CONCAT_WS(' ',firstname,lastname)where dist_id=-1 and cellphone=?";
    public final static String GET_DISTRIBUTORS_IF_OPEN_TO_PUBLIC = " select od.outlet_id , dst.*,ctry.currency_symbol from dist_dtl dst,country ctry ,outlet_dtl od, customer_access access where open_to_public='1'and dst.country_id=ctry.country_id and dst.active_yn='1' and access.username = od.cellphone and od.dist_id = -1 and od.country_id = access.country_id and access.access_id = ?";
    public final static String FETCH_ALL_CUSTOMER_SQL ="select * from outlet_dtl";
    public final static String GET_EMPLOYEE_FOR_CUSTOMER_ORDER_OPEN_PUBLIC = "select emp.employee_id,outlet.*,user.user_role from outlet_dtl outlet,customer_access cust,employees emp,user_access user where cust.access_id=? and cust.username=outlet.cellphone and cust.country_id=outlet.country_id and outlet.dist_id=-1 and emp.employee_id=user.employee_id and emp.dist_manf_id=?";


    public final static String FETCH_DISTRIBUTORS_LIST_SQL ="  select dst.*,ctry.currency_symbol from dist_dtl dst,country ctry  where  dst.country_id=ctry.country_id and dst.active_yn='1' ";

    public final static String FETCH_CUSTOMER_MOBILE_SQL="select cellphone from outlet_dtl where outlet_id=?";
    public final static String FETCH_DISTRIBUTOR_NAME_SQL="select dd.cust_name from dist_dtl dd,order_header oh,outlet_dtl od,customer_access ca where od.dist_id=dd.dist_id and dd.dist_id=oh.distributor_id and od.dist_id=oh.distributor_id and oh.order_status='F'and ca.username=od.cellphone and dd.active_yn='1' and od.active_yn='1' and oh.cust_id=? and oh.bill_no=? ";
    public final static String FETCH_CUSTOMER_EMAIL_ID_SQL="select outlet_email_id from outlet_dtl where outlet_id=?";

    public final static String FETCH_EMPLOYEE_ID_SQL="select emp_id from order_header where bill_no=?";
    public final static String FETCH_DISTRIBUTOR_EMAIL_ID_SQL="select email_id from dist_dtl where dist_id=?";
    public final static String FETCH_DISTRIBUTOR_PASSWORD="select password from user_access where email_id=?";
    public final static String GET_TIMEZONE_FOR_CUSTOMER="select timezone from timezones where country_id=?";
    public final static String GET_IS_OPEN_TO_PUBLIC_DISTRIBUTOR="select open_to_public from dist_dtl where dist_id=?";
    public final static String CHECK_CUSTOMER_CELLPHONE_UNIQUE="select outlet_id from outlet_dtl where cellphone=? and " +
            " dist_id=(select dist_manf_id from employees where employee_id= ?)";
    public final static String FETCH_CUSTOMER_DISTID="select dist_id from outlet_dtl where cellPhone=?";
    public final static String CHECK_CUSTOMER_CELLPHONE="select outlet_id from outlet_dtl where cellphone=? and " +
            " dist_id=?";

}