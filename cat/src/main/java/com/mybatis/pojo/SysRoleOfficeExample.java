package com.mybatis.pojo;

import java.util.ArrayList;
import java.util.List;

public class SysRoleOfficeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysRoleOfficeExample() {
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

        public Criteria andRole_idIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRole_idIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRole_idEqualTo(String value) {
            addCriterion("role_id =", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idNotEqualTo(String value) {
            addCriterion("role_id <>", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idGreaterThan(String value) {
            addCriterion("role_id >", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idGreaterThanOrEqualTo(String value) {
            addCriterion("role_id >=", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idLessThan(String value) {
            addCriterion("role_id <", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idLessThanOrEqualTo(String value) {
            addCriterion("role_id <=", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idLike(String value) {
            addCriterion("role_id like", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idNotLike(String value) {
            addCriterion("role_id not like", value, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idIn(List<String> values) {
            addCriterion("role_id in", values, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idNotIn(List<String> values) {
            addCriterion("role_id not in", values, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idBetween(String value1, String value2) {
            addCriterion("role_id between", value1, value2, "role_id");
            return (Criteria) this;
        }

        public Criteria andRole_idNotBetween(String value1, String value2) {
            addCriterion("role_id not between", value1, value2, "role_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idIsNull() {
            addCriterion("office_id is null");
            return (Criteria) this;
        }

        public Criteria andOffice_idIsNotNull() {
            addCriterion("office_id is not null");
            return (Criteria) this;
        }

        public Criteria andOffice_idEqualTo(String value) {
            addCriterion("office_id =", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idNotEqualTo(String value) {
            addCriterion("office_id <>", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idGreaterThan(String value) {
            addCriterion("office_id >", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idGreaterThanOrEqualTo(String value) {
            addCriterion("office_id >=", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idLessThan(String value) {
            addCriterion("office_id <", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idLessThanOrEqualTo(String value) {
            addCriterion("office_id <=", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idLike(String value) {
            addCriterion("office_id like", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idNotLike(String value) {
            addCriterion("office_id not like", value, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idIn(List<String> values) {
            addCriterion("office_id in", values, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idNotIn(List<String> values) {
            addCriterion("office_id not in", values, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idBetween(String value1, String value2) {
            addCriterion("office_id between", value1, value2, "office_id");
            return (Criteria) this;
        }

        public Criteria andOffice_idNotBetween(String value1, String value2) {
            addCriterion("office_id not between", value1, value2, "office_id");
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