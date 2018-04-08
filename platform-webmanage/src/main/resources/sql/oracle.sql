/*一期用户信息视图*/
create or replace view platform_mobile_user as
select t.id as id,
       t.user_type,
       t.is_valid,
       ry.xb,
       t.tel as telphone,
       t.xm,
       t.account,
       t.password,
       xx.id       as schoolid,
       xx.xxbm as schoolcode,
       xx.xxmc     as schoolname,
       t.weixin,
       t.weibo,
       t.qq,
       t.user_type as usertype
  from cuum.user_info t
  left join cuum.ryjbxx ry
    on ry.id = t.ryjbxx_id
  left join cuum.xxjbsjxx xx
    on xx.id = ry.xxbsh;

