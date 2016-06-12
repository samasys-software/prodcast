package com.primeforce.prodcast.dao;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class DistributorDBSql {
    public final static String DISTRIBUTOR_GET_EMP_SQL = "select emp.employee_id , emp.firstname , emp.lastname , emp.title , emp.email_id , emp.cellphone , emp.homephone, emp.workphone, emp.salary, emp.hire_dt, emp.termination_dt,emp.allowance, emp.address_1 , emp.address_2 , emp.address_3 , emp.city, emp.state , emp.postal_code, emp.country_id , emp.active_yn , emp.type , emp.location , emp.sex , emp.dob, emp.comments , emp.user_id , uaccess.email_id , uaccess.password, uaccess.user_role, uaccess.user_id from employees emp , user_access uaccess where emp.employee_id = uaccess.employee_id and emp.dist_manf_id in ( select dist_manf_id from employees where employee_id = ? ) ";
    public final static String DISTRIBUTOR_GET_DIST_FROM_EMP_SQL = "select dist_manf_id from employees where employee_id = ?";
    public final static String DISTRIBUTOR_CREATE_EMP_SQL = "insert into employees ( dist_manf_id , firstname , lastname , title , " +
            "email_id , cellphone, homephone, workphone, salary , hire_dt, termination_dt, allowance, address_1 , address_2 ," +
            "address_3 , city , state, postal_code, country_id , active_yn , type , location , sex, dob , comments , " +
            "user_id , enter_dt_tm , updt_dt_tm , ip_address) values (" +
            "(?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW() , NOW() , '') ";
    public final static String GET_EMP_FROM_EMAIL = "select employee_id from employees where email_id = ?";
    public final static String DISTRIBUTOR_CREATE_EMP_ACCESS_SQL = "insert into user_access ( cust_id , employee_id , email_id  , password , user_role, " +
            "active_yn , user_id , enter_dt_tm , updt_dt_tm , ip_address )  " +
            "values (0, (select employee_id from employees where email_id = ?) , ? , 'welcome' , ?, 1, ?, NOW() , NOW() , '' )";
    public final static String DISTRIBUTOR_CREATE_AREA_MAPPING = "insert into employee_areamap (employee_id , dist_manf_id , area_id , comments , user_id , " +
            "enter_dt_tm , updt_dt_tm , ip_address ) values ( " +
            " ? , 0 , ? , '' , ? , NOW() , NOW() , '')";
    public final static String FETCH_DISTRIBUTOR_SQL ="select * from `my company information` where setupid = (select dist_manf_id from employees where employee_id = ?)";
    public final static String DISTRIBUTOR_CREATE_BRAND_SQL = "insert into product_brand (product_brand_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW()) ";
    public final static String DISTRIBUTOR_GET_BRAND_SQL = "select * from product_brand where distributor_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_CREATE_AREA_SQL = "insert into area_dtl (area_desc , dist_id , manf_id , user_id , enter_dt_tm ,updt_dt_tm, comments, ip_address) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ? , '127.0.0.1') ";
    public final static String DISTRIBUTOR_GET_AREA_SQL = "select * from area_dtl where dist_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_GET_CATEGORY_SQL = "select * from product_category where distributor_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_CREATE_CATEGORY_SQL = "insert into product_category (product_cag_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm , product_catg_desc) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ?) ";
    public final static String DISTRIBUTOR_GET_SUBCATEGORY_SQL = "select * from product_sub_category where distributor_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_CREATE_SUBCATEGORY_SQL = "insert into product_sub_category (product_subcag_name , distributor_id , manufacturer_id , user_id , enter_dt_tm ,updt_dt_tm , product_subcatg_desc , product_catg_id ) values ( ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? , NOW() , NOW() , ? , ?) ";
    public final static String DISTRIBUTOR_GET_PRODUCTS_SQL = "select * from products where distributor_id = ( select dist_manf_id from employees where employee_id = ?) ";
    public final static String DISTRIBUTOR_CREATE_PRODUCT_SQL = "insert into products (product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , product_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address,enter_dt_tm) " +
            "values ( ? , ? , ? , (select dist_manf_id from employees where employee_id = ? ) , (select dist_manf_id from employees where employee_id = ? ) , ? ,  ? , ? , ? , ? , ? , ? , NOW() , '127.0.0.1' , NOW()) ";

    public final static String ADMIN_ALL_DISTRIBUTORS = "select * from dist_dtl";

    public final static String ADMIN_SAVE_DISTRIBUTOR = "insert into dist_dtl (cust_name,customer_type,firstname,lastname,title," +
            "email_id,cellphone,workphone,homephone,address_1," +
            "address_2,address_3,city,state,`postal code`," +
            "country_id,active_yn,sex,manufacture_yn,comments," +
            "user_id , ip_address, enter_dt_tm , updt_dt_tm, notify_yn," +
            "landmark, manfacture_id , business_id ) values ( " +
            "?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,?,?,?,?," +
            "?,'',NOW() , NOW(), 1, " +
            "'',0,0)";
}
