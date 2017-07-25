package com.primeforce.prodcast.dao;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class DistributorDBSql {
    public final static String CHECK_EMAIL_UNIQUE = "select employee_id from employees where email_id = ?";
    public final static String DIST_GET_EMP_FROM_EMAIL = "select employee_id from employees where email_id = ?";
    public final static String ADMIN_GET_DIST_FROM_EMAIL = "select dist_id from dist_dtl where email_id = ? ";
    public final static String DISTRIBUTOR_GET_EMPLOYEE_SQL = "select area.area_id as area_id, emp.employee_id , emp.firstname , emp.lastname , emp.title , emp.email_id , emp.cellphone , emp.homephone, emp.workphone, emp.salary, emp.hire_dt, emp.termination_dt,emp.allowance, emp.address_1 , emp.address_2 , emp.address_3 , emp.city, emp.state , emp.postal_code, emp.country_id , emp.active_yn , emp.type , emp.location , emp.sex , emp.dob, emp.comments , emp.user_id , uaccess.email_id , uaccess.password, uaccess.user_role, uaccess.user_id,ctry.isd_code,dst.timezone from employees emp , user_access uaccess,employee_areamap area,country ctry,dist_dtl dst where area.employee_id = emp.employee_id and emp.employee_id = uaccess.employee_id  and emp.employee_id = ? and ctry.country_id=emp.country_id and dst.dist_id = emp.dist_manf_id and emp.active_yn='1'";
    public final static String DISTRIBUTOR_GET_EMP_SQL = "select area.area_id as area_id, emp.employee_id , emp.firstname , emp.lastname , emp.title , emp.email_id , emp.cellphone , emp.homephone, emp.workphone, emp.salary, emp.hire_dt, emp.termination_dt,emp.allowance, emp.address_1 , emp.address_2 , emp.address_3 , emp.city, emp.state , emp.postal_code, emp.country_id , emp.active_yn , emp.type , emp.location , emp.sex , emp.dob, emp.comments , emp.user_id , uaccess.email_id , uaccess.password, uaccess.user_role, uaccess.user_id,ctry.isd_code,dst.timezone from employees emp ,country ctry,user_access uaccess,employee_areamap area,dist_dtl dst where area.employee_id = emp.employee_id and emp.employee_id = uaccess.employee_id and emp.dist_manf_id in ( select dist_manf_id from employees where employee_id = ? ) and ctry.country_id=emp.country_id and emp.active_yn='1' and  dst.dist_id = emp.dist_manf_id order by 2,3";
    public final static String DISTRIBUTOR_GET_EXP_SQL = "select * from expense_dtl e , exp_catg_ref c where e.catg_id = c.catg_id and e.user_id = ? and e.enter_dt_tm >= ? and e.enter_dt_tm <= ? ORDER BY e.exp_date";
    public final static String DISTRIBUTOR_GET_EXP_SQL_REPORT = "select * from expense_dtl e , exp_catg_ref c where e.catg_id = c.catg_id and e.user_id = ? and e.exp_date >= ? and e.exp_date <= ? ORDER BY e.exp_date";
    public final static String REPORT_TYPE_SQL = "select * from report_types";
    public final static String DISTRIBUTOR_GET_DIST_FROM_EMP_SQL = "select dist_manf_id from employees where employee_id = ?";
    public final static String DISTRIBUTOR_CREATE_EMP_SQL = "insert into employees ( dist_manf_id , firstname , lastname , title , " +
            "email_id , cellphone, homephone, workphone, salary , hire_dt, termination_dt, allowance, address_1 , address_2 ," +
            "address_3 , city , state, postal_code, country_id , active_yn , type , location , sex, dob , comments , " +
            "user_id , enter_dt_tm , updt_dt_tm , ip_address) values (" +
            "(?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW() , NOW() , '') ";
    public final static String DISTRIBUTOR_UPDATE_EMP_SQL = "update employees set firstname=? , lastname=? , title =?, " +
            "email_id=?, cellphone=?, homephone=?, " +
            "workphone=?, salary=?, hire_dt=?, termination_dt=?, " +
            "allowance=?, address_1=? , address_2 =?," +
            "address_3=? , city=? , state=?, postal_code=?, country_id=? , active_yn=? , " +
            "type=? , location=? , sex=?, dob=? , comments=?, " +
            "updt_dt_tm=NOW() where employee_id=?  ";

    public final static String GET_ALL_REGISTORS = "select * from registration_details where status='1' or status='0'";
    public final static String UPDATE_REGISTRATION_SQL = "update registration_details set  status=?,comments=?,updt_dt_tm=NOW() where registration_id=?";
    public final static String GET_EMP_FROM_EMAIL = "select employee_id from employees where email_id = ?";
    public final static String DISTRIBUTOR_CREATE_EMP_ACCESS_SQL = "insert into user_access ( cust_id , employee_id , email_id  , password , user_role, " +
            "active_yn , user_id , enter_dt_tm , updt_dt_tm , ip_address )  " +
            "values (0, (select employee_id from employees where email_id = ?) , ? , 'welcome' , ?, 1, ?, NOW() , NOW() , '' )";
    public final static String DISTRIBUTOR_UPDATE_EMP_ACCESS_SQL = "update user_access set email_id=?  , user_role=?, " +
            "active_yn=? , updt_dt_tm=NOW() where employee_id =?";
    public final static String DISTRIBUTOR_CREATE_AREA_MAPPING = "insert into employee_areamap (employee_id , dist_manf_id , area_id , comments , user_id , " +
            "enter_dt_tm , updt_dt_tm , ip_address ) values ( " +
            " ? , ? , ? , '' , ? , NOW() , NOW() , '')";
    public final static String DISTRIBUTOR_UPDATE_AREA_MAPPING = "update employee_areamap set area_id=? , updt_dt_tm=NOW(),user_id=? where employee_id=?";

    public final static String FETCH_DISTRIBUTOR_SQL = "select * from my_company_information where dist_id = (select dist_manf_id from employees where employee_id = ?)";
    public final static String DISTRIBUTOR_CREATE_BRAND_SQL = "insert into product_brand (product_brand_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW()) ";
    public final static String DISTRIBUTOR_GET_BRAND_SQL = "select * from product_brand where distributor_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_CREATE_AREA_SQL = "insert into area_dtl (area_desc , dist_id , manf_id , user_id , enter_dt_tm ,updt_dt_tm, comments, ip_address) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ? , '127.0.0.1') ";

    public final static String RETURN_DETAILS_SQL_SP = "call sp_return_product(?,?,?,?,?)";
    public final static String RETURN_DETAILS_SQL = "insert into return_dtl (orderdetailid,product_id,quantity,comments,user_id,create_dt_tm,unitprice,amount,sales_tax,other_tax,subtotal)" +
            " values((select orderdetailid from order_header where bill_no=?),?,?,?,?,NOW()," +
            "(select od.unitprice from order_dtl od where od.orderdetailid=(select orderdetailid from order_header where bill_no=?) and od.product_id=?) " +
            ",quantity*unitprice,(select od.sales_tax from order_dtl od where od.orderdetailid =(select orderdetailid from order_header where bill_no=?) and od.product_id = ?)," +
            "(select od.other_tax from order_dtl od where od.orderdetailid =(select orderdetailid from order_header where bill_no=?)  and od.product_id = ?),(amount+amount*((sales_tax+other_tax)/100))";

    public final static String ORDER_DELETE_SQL = "update order_header set order_status='D' where bill_no=?";


    public final static String ORDER_STATUS_UPDATE_SQL = "update order_header set order_status=? where bill_no=?";



    public final static String DISTRIBUTOR_UPDATE_BRAND_SQL = "update product_brand set product_brand_name=? ,  user_id=?  ,updt_dt_tm=NOW() where product_brand_id=?";
    public final static String DISTRIBUTOR_UPDATE_AREA_SQL = "update  area_dtl set area_desc=? ,  user_id=? , updt_dt_tm=NOW() where area_id=?";
    public final static String DISTRIBUTOR_GET_AREA_SQL = "select * from area_dtl where dist_id = ( select dist_manf_id from employees where employee_id = ?) order by area_desc";
    public final static String DISTRIBUTOR_GET_CATEGORY_SQL = "select * from product_category where distributor_id = ( select dist_manf_id from employees where employee_id = ?) order by product_cag_name ";
    public final static String DISTRIBUTOR_CREATE_CATEGORY_SQL = "insert into product_category (product_cag_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm , product_catg_desc) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ?) ";

    public final static String DISTRIBUTOR_UPDATE_CATEGORY_SQL = "update product_category set product_cag_name =?,  user_id=?,updt_dt_tm=NOW() where product_catg_id=?";

    public final static String DISTRIBUTOR_GET_SUBCATEGORY_SQL = "select * from product_sub_category where distributor_id = ( select dist_manf_id from employees where employee_id = ?) order by product_subcag_name";
    public final static String DISTRIBUTOR_CREATE_SUBCATEGORY_SQL = "insert into product_sub_category (product_subcag_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm , product_subcatg_desc , product_catg_id ) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ? , ?) ";

    public final static String DISTRIBUTOR_UPDATE_SUBCATEGORY_SQL = "update product_sub_category set product_subcag_name=? ,  user_id =?,updt_dt_tm =NOW() where product_sub_catg_id=?";

    public final static String DISTRIBUTOR_GET_PRODUCTS_SQL = "select p.*, b.product_brand_name  from products p, product_brand b where b.product_brand_id = p.product_brand_id and p.distributor_id = ( select dist_manf_id from employees where employee_id = ?) and p.active=1 order by product_name";
    public final static String DISTRIBUTOR_CREATE_PRODUCT_SQL = "insert into products (product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , product_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address,enter_dt_tm,sales_tax, other_tax,retailprice,uom) " +
            "values ( ? , ? , ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? ,  ? , ? , ? , ? , ? , ? , NOW() , '127.0.0.1' , NOW(),?,?,?,?) ";
    public final static String DISTRIBUTOR_UPDATE_PRODUCT_SQL = "update products set product_name=? , product_desc=?, product_sku=? , unitprice=?, price_type=? , prod_catg_id=? , product_sub_catg_id=? , product_brand_id=?, active=?,sales_tax=?, other_tax=?,retailprice=?, uom=?,user_id=?, updt_dt_tm=NOW() where product_id=?";
    public final static String ADMIN_ALL_DISTRIBUTORS = "select dst.*, ctry.currency_symbol from dist_dtl dst , country ctry where dst.country_id = ctry.country_id and active_yn='1' order by cust_name";
    public final static String FETCH_DISTRIBUTOR = "select dst.*, ctry.currency_symbol from dist_dtl dst , country ctry where dst.country_id = ctry.country_id and dst.dist_id = ? ";

    public final static String ADMIN_SAVE_DISTRIBUTOR = "insert into dist_dtl (cust_name,customer_type,firstname,lastname,title," +
            "email_id,cellphone,workphone,homephone,address_1," +
            "address_2,address_3,city,state,`postal code`," +
            "country_id,timezone,active_yn,sex,manufacture_yn,comments," +
            "user_id , open_to_public,ip_address, enter_dt_tm , updt_dt_tm, notify_yn," +
            "landmark, manfacture_id , business_id ) values ( " +
            "?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?,?," +
            "?,?,'',NOW() , NOW(), 1, " +
            "'',0,0)";
    public final static String ADMIN_UPDATE_DISTRIBUTOR = "update dist_dtl set cust_name=?,customer_type=?,firstname=?,lastname=?,title=?," +
            "email_id=?,cellphone=?,workphone=?,homephone=?,address_1=?," +
            "address_2=?,address_3=?,city=?,state=?,`postal code`=?," +
            "country_id=?,timezone=?,active_yn=?,sex=?,manufacture_yn=?,comments=?,user_id =?,open_to_public=?,updt_dt_tm=NOW() where dist_id=?";

    public final static String ADMIN_UPDATE_SETTINGS = "update my_company_information set salestaxrate=?,companyname=?,address=?,city=?,stateorprovince=?,postalcode=?,country_id=?,phonenumber=?,faxnumber=?,timezone=?,user_id=?,updt_dt_tm=NOW() where dist_id=?";

    public final static String ADMIN_UPDATE_DISTRIBUTOR_SETTINGS = "update dist_dtl set cust_name=?,user_id=?,updt_dt_tm=NOW() where dist_id=?";


    public final static String ADMIN_UPDATE_EMPLOYEE_SETTINGS = "update employees set address_1=?,address_2=?,address_3=?,city=?,state=?,postal_code=?,country_id=?,cellphone=?,updt_dt_tm=NOW() where employee_id=?";


    public final static String CREATE_SETTING_SQL = "insert into my_company_information (dist_id, salestaxrate , companyname , address  , city , stateorprovince,postalcode,country_id,phonenumber,faxnumber,timezone,user_id,ip_address,updt_dt_tm)values( ?,?,?,?,?,?,?,?,?,?,?,?,'',Now())";


    public final static String CREATE_EXPENSE_SQL = "insert into expense_dtl (ref_no, exp_date, account, catg_id, desc1, desc2, amount, pay_mode, enter_dt_tm, ip_address, user_id, updt_dt_tm ) values (?, ?, ?, ?, ?, ?, ?, ?, NOW(), '', ?, NOW())";
    public final static String UPDATE_EXPENSE_SQL = "update expense_dtl set account=?, catg_id=?, desc1=?, desc2=?, amount=?, pay_mode=?, user_id=? ,exp_date=?, updt_dt_tm=NOW() where id=?";
    public final static String GET_REPORT_TYPE_FROM_SQL = "select report_sql from report_types where report_id=?";
    public final static String CREATE_EXPENSE_CATEGORY = "insert into exp_catg_ref (catg_desc, add_date, ip_address, user_id, updt_dt_tm, dist_manf_id ) values (?, NOW(), '', ?, NOW() , (select dist_manf_id from employees where employee_id = ?))";
    public final static String UPDATE_EXPENSE_CATEGORY = "update exp_catg_ref set catg_desc=?, user_id=?, updt_dt_tm=NOW() where catg_id=? ";
    public final static String DISTRIBUTOR_GET_EXPENSE_CATEGORY = "select * from exp_catg_ref where dist_manf_id = ( select dist_manf_id from employees where employee_id = ? ) order by catg_desc";
    public static final String[] ABCS_SQL = new String[]{DISTRIBUTOR_UPDATE_AREA_SQL, DISTRIBUTOR_UPDATE_BRAND_SQL,
            DISTRIBUTOR_UPDATE_CATEGORY_SQL,
            DISTRIBUTOR_UPDATE_SUBCATEGORY_SQL};


  public final static String ADMIN_EMPLOYEE_ID="select employee_id from employees where email_id=(select email_id from dist_dtl where dist_id=?)";

    public final static String FETCH_BILLDETAILS_FOR_CUSTOMERS = "select oh.orderdetailid,cust.outlet_name, oh.bill_no,oh.bill_date, oh.total_amt,oh.outstanding_balance from order_header oh,customer_access ca,outlet_dtl cust where ca.access_id=? and ca.username=cust.cellphone and oh.cust_id=cust.outlet_id and ca.country_id=cust.country_id  and oh.bill_date between ? and ?  and  oh.distributor_id=?";
    public final static String GET_BRAND_SQL = "select product_brand_id from product_brand where product_brand_name=? and distributor_id=(select dist_manf_id from employees where employee_id = ?)";
    public final static String GET_CATEGORY_SQL = "select product_catg_id from product_category where product_cag_name=? and distributor_id=(select dist_manf_id from employees where employee_id = ? )";
    public final static String GET_SUB_CATEGORY_SQL = "select product_sub_catg_id from product_sub_category where product_catg_id=? and product_subcag_name=? and distributor_id=(select dist_manf_id from employees where employee_id = ? )";
    public final static String GET_PRODUCT_SQL = "select product_id from products where product_sku=? and distributor_id=(select dist_manf_id from employees where employee_id = ? )";


}
