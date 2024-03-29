package com.mybatis.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysAreaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysAreaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParent_idIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParent_idIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParent_idEqualTo(String value) {
            addCriterion("parent_id =", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idGreaterThan(String value) {
            addCriterion("parent_id >", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idLessThan(String value) {
            addCriterion("parent_id <", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idLike(String value) {
            addCriterion("parent_id like", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idNotLike(String value) {
            addCriterion("parent_id not like", value, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idIn(List<String> values) {
            addCriterion("parent_id in", values, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parent_id");
            return (Criteria) this;
        }

        public Criteria andParent_idNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parent_id");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameIsNull() {
            addCriterion("simple_name is null");
            return (Criteria) this;
        }

        public Criteria andSimple_nameIsNotNull() {
            addCriterion("simple_name is not null");
            return (Criteria) this;
        }

        public Criteria andSimple_nameEqualTo(String value) {
            addCriterion("simple_name =", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameNotEqualTo(String value) {
            addCriterion("simple_name <>", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameGreaterThan(String value) {
            addCriterion("simple_name >", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameGreaterThanOrEqualTo(String value) {
            addCriterion("simple_name >=", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameLessThan(String value) {
            addCriterion("simple_name <", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameLessThanOrEqualTo(String value) {
            addCriterion("simple_name <=", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameLike(String value) {
            addCriterion("simple_name like", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameNotLike(String value) {
            addCriterion("simple_name not like", value, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameIn(List<String> values) {
            addCriterion("simple_name in", values, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameNotIn(List<String> values) {
            addCriterion("simple_name not in", values, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameBetween(String value1, String value2) {
            addCriterion("simple_name between", value1, value2, "simple_name");
            return (Criteria) this;
        }

        public Criteria andSimple_nameNotBetween(String value1, String value2) {
            addCriterion("simple_name not between", value1, value2, "simple_name");
            return (Criteria) this;
        }

        public Criteria andZip_codeIsNull() {
            addCriterion("zip_code is null");
            return (Criteria) this;
        }

        public Criteria andZip_codeIsNotNull() {
            addCriterion("zip_code is not null");
            return (Criteria) this;
        }

        public Criteria andZip_codeEqualTo(String value) {
            addCriterion("zip_code =", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeNotEqualTo(String value) {
            addCriterion("zip_code <>", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeGreaterThan(String value) {
            addCriterion("zip_code >", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeGreaterThanOrEqualTo(String value) {
            addCriterion("zip_code >=", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeLessThan(String value) {
            addCriterion("zip_code <", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeLessThanOrEqualTo(String value) {
            addCriterion("zip_code <=", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeLike(String value) {
            addCriterion("zip_code like", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeNotLike(String value) {
            addCriterion("zip_code not like", value, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeIn(List<String> values) {
            addCriterion("zip_code in", values, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeNotIn(List<String> values) {
            addCriterion("zip_code not in", values, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeBetween(String value1, String value2) {
            addCriterion("zip_code between", value1, value2, "zip_code");
            return (Criteria) this;
        }

        public Criteria andZip_codeNotBetween(String value1, String value2) {
            addCriterion("zip_code not between", value1, value2, "zip_code");
            return (Criteria) this;
        }

        public Criteria andArea_numberIsNull() {
            addCriterion("area_number is null");
            return (Criteria) this;
        }

        public Criteria andArea_numberIsNotNull() {
            addCriterion("area_number is not null");
            return (Criteria) this;
        }

        public Criteria andArea_numberEqualTo(String value) {
            addCriterion("area_number =", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberNotEqualTo(String value) {
            addCriterion("area_number <>", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberGreaterThan(String value) {
            addCriterion("area_number >", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberGreaterThanOrEqualTo(String value) {
            addCriterion("area_number >=", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberLessThan(String value) {
            addCriterion("area_number <", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberLessThanOrEqualTo(String value) {
            addCriterion("area_number <=", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberLike(String value) {
            addCriterion("area_number like", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberNotLike(String value) {
            addCriterion("area_number not like", value, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberIn(List<String> values) {
            addCriterion("area_number in", values, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberNotIn(List<String> values) {
            addCriterion("area_number not in", values, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberBetween(String value1, String value2) {
            addCriterion("area_number between", value1, value2, "area_number");
            return (Criteria) this;
        }

        public Criteria andArea_numberNotBetween(String value1, String value2) {
            addCriterion("area_number not between", value1, value2, "area_number");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Byte value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Byte value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Byte value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Byte value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Byte value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Byte> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Byte> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Byte value1, Byte value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andPath_idsIsNull() {
            addCriterion("path_ids is null");
            return (Criteria) this;
        }

        public Criteria andPath_idsIsNotNull() {
            addCriterion("path_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPath_idsEqualTo(String value) {
            addCriterion("path_ids =", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsNotEqualTo(String value) {
            addCriterion("path_ids <>", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsGreaterThan(String value) {
            addCriterion("path_ids >", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsGreaterThanOrEqualTo(String value) {
            addCriterion("path_ids >=", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsLessThan(String value) {
            addCriterion("path_ids <", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsLessThanOrEqualTo(String value) {
            addCriterion("path_ids <=", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsLike(String value) {
            addCriterion("path_ids like", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsNotLike(String value) {
            addCriterion("path_ids not like", value, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsIn(List<String> values) {
            addCriterion("path_ids in", values, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsNotIn(List<String> values) {
            addCriterion("path_ids not in", values, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsBetween(String value1, String value2) {
            addCriterion("path_ids between", value1, value2, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_idsNotBetween(String value1, String value2) {
            addCriterion("path_ids not between", value1, value2, "path_ids");
            return (Criteria) this;
        }

        public Criteria andPath_namesIsNull() {
            addCriterion("path_names is null");
            return (Criteria) this;
        }

        public Criteria andPath_namesIsNotNull() {
            addCriterion("path_names is not null");
            return (Criteria) this;
        }

        public Criteria andPath_namesEqualTo(String value) {
            addCriterion("path_names =", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesNotEqualTo(String value) {
            addCriterion("path_names <>", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesGreaterThan(String value) {
            addCriterion("path_names >", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesGreaterThanOrEqualTo(String value) {
            addCriterion("path_names >=", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesLessThan(String value) {
            addCriterion("path_names <", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesLessThanOrEqualTo(String value) {
            addCriterion("path_names <=", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesLike(String value) {
            addCriterion("path_names like", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesNotLike(String value) {
            addCriterion("path_names not like", value, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesIn(List<String> values) {
            addCriterion("path_names in", values, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesNotIn(List<String> values) {
            addCriterion("path_names not in", values, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesBetween(String value1, String value2) {
            addCriterion("path_names between", value1, value2, "path_names");
            return (Criteria) this;
        }

        public Criteria andPath_namesNotBetween(String value1, String value2) {
            addCriterion("path_names not between", value1, value2, "path_names");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDel_flagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDel_flagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDel_flagEqualTo(String value) {
            addCriterion("del_flag =", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagGreaterThan(String value) {
            addCriterion("del_flag >", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagLessThan(String value) {
            addCriterion("del_flag <", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagLike(String value) {
            addCriterion("del_flag like", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagNotLike(String value) {
            addCriterion("del_flag not like", value, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagIn(List<String> values) {
            addCriterion("del_flag in", values, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "del_flag");
            return (Criteria) this;
        }

        public Criteria andDel_flagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "del_flag");
            return (Criteria) this;
        }

        public Criteria andCreate_dateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreate_dateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_dateEqualTo(Date value) {
            addCriterion("create_date =", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateGreaterThan(Date value) {
            addCriterion("create_date >", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateLessThan(Date value) {
            addCriterion("create_date <", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateIn(List<Date> values) {
            addCriterion("create_date in", values, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_dateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "create_date");
            return (Criteria) this;
        }

        public Criteria andCreate_byIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreate_byIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_byEqualTo(String value) {
            addCriterion("create_by =", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotEqualTo(String value) {
            addCriterion("create_by <>", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byGreaterThan(String value) {
            addCriterion("create_by >", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLessThan(String value) {
            addCriterion("create_by <", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLike(String value) {
            addCriterion("create_by like", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotLike(String value) {
            addCriterion("create_by not like", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byIn(List<String> values) {
            addCriterion("create_by in", values, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotIn(List<String> values) {
            addCriterion("create_by not in", values, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "create_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateEqualTo(Date value) {
            addCriterion("update_date =", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateGreaterThan(Date value) {
            addCriterion("update_date >", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateLessThan(Date value) {
            addCriterion("update_date <", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateIn(List<Date> values) {
            addCriterion("update_date in", values, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_dateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "update_date");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdate_byEqualTo(String value) {
            addCriterion("update_by =", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotEqualTo(String value) {
            addCriterion("update_by <>", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byGreaterThan(String value) {
            addCriterion("update_by >", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLessThan(String value) {
            addCriterion("update_by <", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLike(String value) {
            addCriterion("update_by like", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotLike(String value) {
            addCriterion("update_by not like", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIn(List<String> values) {
            addCriterion("update_by in", values, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotIn(List<String> values) {
            addCriterion("update_by not in", values, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "update_by");
            return (Criteria) this;
        }

        public Criteria andParent_idsIsNull() {
            addCriterion("parent_ids is null");
            return (Criteria) this;
        }

        public Criteria andParent_idsIsNotNull() {
            addCriterion("parent_ids is not null");
            return (Criteria) this;
        }

        public Criteria andParent_idsEqualTo(String value) {
            addCriterion("parent_ids =", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsNotEqualTo(String value) {
            addCriterion("parent_ids <>", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsGreaterThan(String value) {
            addCriterion("parent_ids >", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsGreaterThanOrEqualTo(String value) {
            addCriterion("parent_ids >=", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsLessThan(String value) {
            addCriterion("parent_ids <", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsLessThanOrEqualTo(String value) {
            addCriterion("parent_ids <=", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsLike(String value) {
            addCriterion("parent_ids like", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsNotLike(String value) {
            addCriterion("parent_ids not like", value, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsIn(List<String> values) {
            addCriterion("parent_ids in", values, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsNotIn(List<String> values) {
            addCriterion("parent_ids not in", values, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsBetween(String value1, String value2) {
            addCriterion("parent_ids between", value1, value2, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andParent_idsNotBetween(String value1, String value2) {
            addCriterion("parent_ids not between", value1, value2, "parent_ids");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andShipping_groupIsNull() {
            addCriterion("shipping_group is null");
            return (Criteria) this;
        }

        public Criteria andShipping_groupIsNotNull() {
            addCriterion("shipping_group is not null");
            return (Criteria) this;
        }

        public Criteria andShipping_groupEqualTo(String value) {
            addCriterion("shipping_group =", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupNotEqualTo(String value) {
            addCriterion("shipping_group <>", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupGreaterThan(String value) {
            addCriterion("shipping_group >", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupGreaterThanOrEqualTo(String value) {
            addCriterion("shipping_group >=", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupLessThan(String value) {
            addCriterion("shipping_group <", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupLessThanOrEqualTo(String value) {
            addCriterion("shipping_group <=", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupLike(String value) {
            addCriterion("shipping_group like", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupNotLike(String value) {
            addCriterion("shipping_group not like", value, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupIn(List<String> values) {
            addCriterion("shipping_group in", values, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupNotIn(List<String> values) {
            addCriterion("shipping_group not in", values, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupBetween(String value1, String value2) {
            addCriterion("shipping_group between", value1, value2, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andShipping_groupNotBetween(String value1, String value2) {
            addCriterion("shipping_group not between", value1, value2, "shipping_group");
            return (Criteria) this;
        }

        public Criteria andStore_idIsNull() {
            addCriterion("store_id is null");
            return (Criteria) this;
        }

        public Criteria andStore_idIsNotNull() {
            addCriterion("store_id is not null");
            return (Criteria) this;
        }

        public Criteria andStore_idEqualTo(String value) {
            addCriterion("store_id =", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idNotEqualTo(String value) {
            addCriterion("store_id <>", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idGreaterThan(String value) {
            addCriterion("store_id >", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idGreaterThanOrEqualTo(String value) {
            addCriterion("store_id >=", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idLessThan(String value) {
            addCriterion("store_id <", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idLessThanOrEqualTo(String value) {
            addCriterion("store_id <=", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idLike(String value) {
            addCriterion("store_id like", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idNotLike(String value) {
            addCriterion("store_id not like", value, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idIn(List<String> values) {
            addCriterion("store_id in", values, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idNotIn(List<String> values) {
            addCriterion("store_id not in", values, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idBetween(String value1, String value2) {
            addCriterion("store_id between", value1, value2, "store_id");
            return (Criteria) this;
        }

        public Criteria andStore_idNotBetween(String value1, String value2) {
            addCriterion("store_id not between", value1, value2, "store_id");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}