package com.primeforce.prodcast.dao;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class DBSql {
    public final static String LOGIN_SQL_NEW = "select emp.employee_id , emp.firstname , emp.lastname , emp.title , emp.email_id , emp.cellphone , emp.homephone, emp.workphone, emp.salary, emp.hire_dt, emp.termination_dt,emp.allowance, emp.address_1 , emp.address_2 , emp.address_3 , emp.city, emp.state , emp.postal_code, emp.country_id , emp.active_yn , emp.type , emp.location , emp.sex , emp.dob, emp.comments , emp.user_id , uaccess.email_id , uaccess.password, uaccess.user_role, uaccess.user_id from employees emp , user_access uaccess where emp.employee_id = uaccess.employee_id and emp.email_id = ? and uaccess.password = ?";
    //public final static String LOGIN_SQL = "select emp.employee_id, emp.firstname, emp.lastname, emp.type, emp_access.user_name  from employees emp, emp_access emp_access where emp_access.emp_id = emp.employee_id and emp_access.user_name=? and emp_access.password=?";
    public final static String CUSTOMER_SEARCH_SQL = "select * from outlet_dtl where area in ( select area_id from employee_areamap where employee_id = ? )";
    public final static String CUSTOMER_SEARCH_SQL_D = "select * from outlet_dtl where area in ( select area_id from area_dtl where dist_id = (select dist_manf_id from employees where employee_id =? ) )";
    public final static String GET_EMP_ROLE = "select user_role from user_access where employee_id = ?";
    public final static String CUSTOMER_SQL = "select outlet_id , outlet_name , address_1, address_2, address_3, city, state, postal_code, country_id, outlet_type, area, firstname, lastname, title, cellphone, workphone, outlet_email_id, comments, week_day  from outlet_dtl where outlet_id=?";
    public final static String PRODUCT_SEARCH_SQL = " select * from products where dist_id = (select dist_manf_id from employees where employee_id = ? )";
    public final static String OUTSTANDING_BILL_SEARCH = "select oh.cust_id , oh.bill_no , oh.total_amt , oh.bill_date , oh.outstanding_balance ,'' as companyname from  order_header oh where oh.cust_id = ? and oh.outstanding_balance >0 ";
    public final static String COLLECTION_UPDATE_SQL = "call sp_collection_update (? , ? , ? ) ";

    public final static String ORDER_DETAILS_SQL = "insert into order_dtl (orderdetailid, product_id , quantity, unitprice, amount) values( ? , ? , ?, (select unitprice from products where product_id = ?) , quantity*unitprice )";
    public final static String ORDER_TOTAL_SQL = "update order_header set total_amt = (select sum( amount) from order_dtl where orderdetailid = ? ) , outstanding_balance = total_amt where orderdetailid = ? ";

    public final static String FETCH_ORDER_SQL = " select oh.bill_date, oh.orderdetailid , oh.bill_no, oh.cust_id, cust.outlet_name, oh.emp_id , " +
            "emp.firstname , emp.lastname, oh.total_amt, oh.enter_dt_tm , oh.outstanding_balance " +
            "from order_header oh , employees emp , outlet_dtl cust where " +
            "oh.cust_id = cust.outlet_id  and oh.emp_id = emp.employee_id and oh.bill_no=?";
    public final static String FETCH_ORDER_DTL_SQL = "select od.product_id , od.quantity , od.unitprice, od.amount , pr.product_name from order_dtl od, products pr where od.product_id = pr.product_id and od.orderdetailid = ?";
    public final static String FETCH_ORDER_COLLECTION_SQL = "select cd.bill_no, cd.amount_paid, cd.emp_id , emp.firstname , emp.lastname, cd.payment_type, cd.payment_date , '' as outlet_name from collection_dtl cd , employees emp where emp.employee_id = cd.emp_id and bill_no=?";

    public final static String REPORT_SALES_SQL = "select oh.orderdetailid, oh.bill_no,oh.bill_date, oh.enter_dt_tm, oh.total_amt, oh.outstanding_balance, cust.outlet_name  from order_header oh , outlet_dtl cust where oh.cust_id = cust.outlet_id and oh.bill_date between ? and ? and oh.emp_id = ?";
    public final static String REPORT_COLLECTION_SQL = "select cd.bill_no, cd.amount_paid , cd.emp_id , emp.firstname , emp.lastname , cd.payment_type , cd.payment_date, cust.outlet_name  from collection_dtl cd , outlet_dtl cust , employees emp where emp.employee_id = cd.emp_id and cd.cust_id = cust.outlet_id and cd.payment_date between ? and ? and cd.emp_id = ?";
    public final static String FETCH_AREAS_SQL = "select * from area_dtl where area_id in ( select area_id from employee_areamap where employee_id = ?)";
    public final static String CREATE_CUSTOMER_SQL = "insert into outlet_dtl ( dist_id , outlet_name , outlet_type , area, week_day,firstname ," +
            "lastname , outlet_email_id, cellphone, workphone, landmark, " +
            "address_1 , address_2 , address_3 , city , state ,country_id , postal_code " +
            ", comments  , enter_dt_tm , user_id, title, bus_id,  homephone,sex,notify_yn, ip_address,updt_dt_tm ,active_yn) values (" +
            "(select dist_manf_id from employees where employee_id =?), ?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?,?,?," +
            "?,NOW(),?,'',0,'','', 1 ,'', NOW(),1) ";

    public final static String CREATE_EXPENSE_SQL = "insert into expense_dtl (ref_no, exp_date, account, catg_id, desc1, desc2, amount, pay_mode, enter_dt_tm, ip_address, user_id, updt_dt_tm ) values (?, NOW(), ?, ?, ?, ?, ?, ?, NOW(), ?, ?, NOW())";

    public final static String CREATE_EXPENSE_CATEGORY = "insert into exp_catg_ref (catg_desc, add_date, ip_address, user_id, updt_dt_tm) values (?, NOW(), ?, ?, NOW())";

}
