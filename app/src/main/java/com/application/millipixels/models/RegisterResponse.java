package com.application.millipixels.models;

import java.util.List;

public class RegisterResponse {


    /**
     * status : true
     * code : 200
     * data : {"token":"token","user":{"id":4,"name":"gaurav","email":"gaurav.sethi@yopm2ail.com","social_token":"1dssdffdsfffsdfsdfsdffs","provider":"GOOGLE","photo_url":"https://www.gravatar.com/avatar/8a3f161746e7288bf4b01bc2d7d1f981.jpg?s=200&d=mm","uses_two_factor_auth":false,"current_team_id":{},"stripe_id":{},"current_billing_plan":{},"billing_state":{},"vat_id":{},"trial_ends_at":{},"last_read_announcements_at":{},"created_at":"2018-05-10 06:06:17","updated_at":"2018-05-10 06:06:42","tax_rate":0}}
     */

    private boolean status;
    private int code;
    private DataBean data;
//    private ErrorBean error;
//
//    public ErrorBean getError() {
//        return error;
//    }
//
//    public void setError(ErrorBean error) {
//        this.error = error;
//    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : token
         * user : {"id":4,"name":"gaurav","email":"gaurav.sethi@yopm2ail.com","social_token":"1dssdffdsfffsdfsdfsdffs","provider":"GOOGLE","photo_url":"https://www.gravatar.com/avatar/8a3f161746e7288bf4b01bc2d7d1f981.jpg?s=200&d=mm","uses_two_factor_auth":false,"current_team_id":{},"stripe_id":{},"current_billing_plan":{},"billing_state":{},"vat_id":{},"trial_ends_at":{},"last_read_announcements_at":{},"created_at":"2018-05-10 06:06:17","updated_at":"2018-05-10 06:06:42","tax_rate":0}
         */

        private String token;
        private UserBean user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 4
             * name : gaurav
             * email : gaurav.sethi@yopm2ail.com
             * social_token : 1dssdffdsfffsdfsdfsdffs
             * provider : GOOGLE
             * photo_url : https://www.gravatar.com/avatar/8a3f161746e7288bf4b01bc2d7d1f981.jpg?s=200&d=mm
             * uses_two_factor_auth : false
             * current_team_id : {}
             * stripe_id : {}
             * current_billing_plan : {}
             * billing_state : {}
             * vat_id : {}
             * trial_ends_at : {}
             * last_read_announcements_at : {}
             * created_at : 2018-05-10 06:06:17
             * updated_at : 2018-05-10 06:06:42
             * tax_rate : 0
             */

            private int id;
            private String name;
            private String email;
            private String social_token;
            private String provider;
            private String photo_url;
            private boolean uses_two_factor_auth;
            private CurrentTeamIdBean current_team_id;
            private StripeIdBean stripe_id;
            private CurrentBillingPlanBean current_billing_plan;
            private BillingStateBean billing_state;
            private VatIdBean vat_id;
            private TrialEndsAtBean trial_ends_at;
            private LastReadAnnouncementsAtBean last_read_announcements_at;
            private String created_at;
            private String updated_at;
            private int tax_rate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getSocial_token() {
                return social_token;
            }

            public void setSocial_token(String social_token) {
                this.social_token = social_token;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }

            public String getPhoto_url() {
                return photo_url;
            }

            public void setPhoto_url(String photo_url) {
                this.photo_url = photo_url;
            }

            public boolean isUses_two_factor_auth() {
                return uses_two_factor_auth;
            }

            public void setUses_two_factor_auth(boolean uses_two_factor_auth) {
                this.uses_two_factor_auth = uses_two_factor_auth;
            }

            public CurrentTeamIdBean getCurrent_team_id() {
                return current_team_id;
            }

            public void setCurrent_team_id(CurrentTeamIdBean current_team_id) {
                this.current_team_id = current_team_id;
            }

            public StripeIdBean getStripe_id() {
                return stripe_id;
            }

            public void setStripe_id(StripeIdBean stripe_id) {
                this.stripe_id = stripe_id;
            }

            public CurrentBillingPlanBean getCurrent_billing_plan() {
                return current_billing_plan;
            }

            public void setCurrent_billing_plan(CurrentBillingPlanBean current_billing_plan) {
                this.current_billing_plan = current_billing_plan;
            }

            public BillingStateBean getBilling_state() {
                return billing_state;
            }

            public void setBilling_state(BillingStateBean billing_state) {
                this.billing_state = billing_state;
            }

            public VatIdBean getVat_id() {
                return vat_id;
            }

            public void setVat_id(VatIdBean vat_id) {
                this.vat_id = vat_id;
            }

            public TrialEndsAtBean getTrial_ends_at() {
                return trial_ends_at;
            }

            public void setTrial_ends_at(TrialEndsAtBean trial_ends_at) {
                this.trial_ends_at = trial_ends_at;
            }

            public LastReadAnnouncementsAtBean getLast_read_announcements_at() {
                return last_read_announcements_at;
            }

            public void setLast_read_announcements_at(LastReadAnnouncementsAtBean last_read_announcements_at) {
                this.last_read_announcements_at = last_read_announcements_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getTax_rate() {
                return tax_rate;
            }

            public void setTax_rate(int tax_rate) {
                this.tax_rate = tax_rate;
            }

            public static class CurrentTeamIdBean {
            }

            public static class StripeIdBean {
            }

            public static class CurrentBillingPlanBean {
            }

            public static class BillingStateBean {
            }

            public static class VatIdBean {
            }

            public static class TrialEndsAtBean {
            }

            public static class LastReadAnnouncementsAtBean {
            }
        }
    }

    public static class ErrorBean {
        /**
         * code : 422
         * error_message : {"message":["The mobile must be a number."]}
         */

        private int code;
        private LoginResponse.ErrorBean.ErrorMessageBean error_message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public LoginResponse.ErrorBean.ErrorMessageBean getError_message() {
            return error_message;
        }

        public void setError_message(LoginResponse.ErrorBean.ErrorMessageBean error_message) {
            this.error_message = error_message;
        }

        public static class ErrorMessageBean {
            private List<String> message;

            public List<String> getMessage() {
                return message;
            }

            public void setMessage(List<String> message) {
                this.message = message;
            }
        }
    }


}
