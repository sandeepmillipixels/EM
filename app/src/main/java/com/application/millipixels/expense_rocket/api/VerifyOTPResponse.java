package com.application.millipixels.expense_rocket.api;

public class VerifyOTPResponse {

    /**
     * status : true
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjBlZTg5MGRiZGM5MzFjNTA3ZjAyZWYwZmJiYWJmOWU1NGE3YzY3NTE5YjQ1OTQxNWQxZTk0ZjQ0ODhiNTM3ZmVhYzgyYjFmNTRmYjVlZjAzIn0.eyJhdWQiOiIxIiwianRpIjoiMGVlODkwZGJkYzkzMWM1MDdmMDJlZjBmYmJhYmY5ZTU0YTdjNjc1MTliNDU5NDE1ZDFlOTRmNDQ4OGI1MzdmZWFjODJiMWY1NGZiNWVmMDMiLCJpYXQiOjE1MjQ4MzcyMDcsIm5iZiI6MTUyNDgzNzIwNywiZXhwIjoxNTU2MzczMjA3LCJzdWIiOiIyMSIsInNjb3BlcyI6W119.n86S-UhoTCeaoKc7p1vbfVlwhcKAAFJWyzNfKNJNJ17k-iYzsh6x-s66FU889K-1CXQ9Jb9SiGz8j69T5UhZEXcm6VpiMxBXPPNUhDcKZuiuI4oEZjBjR9J-StwRNhDqbzwQJtdrQT85HHi8cmgXdjVNosXmYeegN_nmTOsH_MungYjhg6Gj2gyOQkajwDWURcz3cq5upVzn3ls2Esaa5RKqrld-GFvl1Eu55jijm2LkbdE0FfXegWHZr3wpTCDsvxfNhCjXHd1FpYmTyFUMUdDpfQgf4975AsqYIicmqfwYFWcziW6-PboihwUXPJxXR0qmGUrf2NYJCJoceeYx32hwuLqbrhalHKG6wwdD59JGe8b2VHaH8JaxaM4HljGUMyxvgquQxdqiZel6Th-3vieY64vinUdD6rUsdnnC5NPynx21cSCvXcChVm6pKGdFnekkAgSxMOB3b0EIJSj8fKqkQlpPDI-maKladuPA-FhqJG897_jnXL7qoDfZGsp-R7S9kzgO9LxRZeoEdqCfW4nDCCB_GYM9klctkdems9Uxp1Md2hF73q0vUlfK74rF5iJU5E70yMfU0yHjekECbZLsVeqTSCXLdfUg8a9S2jz00Yx9CUiJaehQeulNQnLK5-6izCUzln4o3_gNBNH7By4Qe8b7mTKY6wFz6mVB11o","User":{"id":21,"name":"No Name","email":"917009331805@tempdomain.com","photo_url":"https://www.gravatar.com/avatar/f3fcc940715d61c51c716ae2604830b2.jpg?s=200&d=mm","uses_two_factor_auth":false,"current_team_id":null,"stripe_id":null,"current_billing_plan":null,"billing_state":null,"vat_id":null,"trial_ends_at":null,"last_read_announcements_at":null,"created_at":null,"updated_at":null,"city_id":null,"state_id":null,"country_id":null,"tax_rate":0}}
     * error : null
     */

    private boolean status;
    private DataBean data;
    private ErrorResponse error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjBlZTg5MGRiZGM5MzFjNTA3ZjAyZWYwZmJiYWJmOWU1NGE3YzY3NTE5YjQ1OTQxNWQxZTk0ZjQ0ODhiNTM3ZmVhYzgyYjFmNTRmYjVlZjAzIn0.eyJhdWQiOiIxIiwianRpIjoiMGVlODkwZGJkYzkzMWM1MDdmMDJlZjBmYmJhYmY5ZTU0YTdjNjc1MTliNDU5NDE1ZDFlOTRmNDQ4OGI1MzdmZWFjODJiMWY1NGZiNWVmMDMiLCJpYXQiOjE1MjQ4MzcyMDcsIm5iZiI6MTUyNDgzNzIwNywiZXhwIjoxNTU2MzczMjA3LCJzdWIiOiIyMSIsInNjb3BlcyI6W119.n86S-UhoTCeaoKc7p1vbfVlwhcKAAFJWyzNfKNJNJ17k-iYzsh6x-s66FU889K-1CXQ9Jb9SiGz8j69T5UhZEXcm6VpiMxBXPPNUhDcKZuiuI4oEZjBjR9J-StwRNhDqbzwQJtdrQT85HHi8cmgXdjVNosXmYeegN_nmTOsH_MungYjhg6Gj2gyOQkajwDWURcz3cq5upVzn3ls2Esaa5RKqrld-GFvl1Eu55jijm2LkbdE0FfXegWHZr3wpTCDsvxfNhCjXHd1FpYmTyFUMUdDpfQgf4975AsqYIicmqfwYFWcziW6-PboihwUXPJxXR0qmGUrf2NYJCJoceeYx32hwuLqbrhalHKG6wwdD59JGe8b2VHaH8JaxaM4HljGUMyxvgquQxdqiZel6Th-3vieY64vinUdD6rUsdnnC5NPynx21cSCvXcChVm6pKGdFnekkAgSxMOB3b0EIJSj8fKqkQlpPDI-maKladuPA-FhqJG897_jnXL7qoDfZGsp-R7S9kzgO9LxRZeoEdqCfW4nDCCB_GYM9klctkdems9Uxp1Md2hF73q0vUlfK74rF5iJU5E70yMfU0yHjekECbZLsVeqTSCXLdfUg8a9S2jz00Yx9CUiJaehQeulNQnLK5-6izCUzln4o3_gNBNH7By4Qe8b7mTKY6wFz6mVB11o
         * User : {"id":21,"name":"No Name","email":"917009331805@tempdomain.com","photo_url":"https://www.gravatar.com/avatar/f3fcc940715d61c51c716ae2604830b2.jpg?s=200&d=mm","uses_two_factor_auth":false,"current_team_id":null,"stripe_id":null,"current_billing_plan":null,"billing_state":null,"vat_id":null,"trial_ends_at":null,"last_read_announcements_at":null,"created_at":null,"updated_at":null,"city_id":null,"state_id":null,"country_id":null,"tax_rate":0}
         */

        private String token;
        private UserBean User;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return User;
        }

        public void setUser(UserBean User) {
            this.User = User;
        }

        public static class UserBean {
            /**
             * id : 21
             * name : No Name
             * email : 917009331805@tempdomain.com
             * photo_url : https://www.gravatar.com/avatar/f3fcc940715d61c51c716ae2604830b2.jpg?s=200&d=mm
             * uses_two_factor_auth : false
             * current_team_id : null
             * stripe_id : null
             * current_billing_plan : null
             * billing_state : null
             * vat_id : null
             * trial_ends_at : null
             * last_read_announcements_at : null
             * created_at : null
             * updated_at : null
             * city_id : null
             * state_id : null
             * country_id : null
             * tax_rate : 0
             */

            private int id;
            private String name;
            private String email;
            private String photo_url;
            private boolean uses_two_factor_auth;
            private Object current_team_id;
            private Object stripe_id;
            private Object current_billing_plan;
            private Object billing_state;
            private Object vat_id;
            private Object trial_ends_at;
            private Object last_read_announcements_at;
            private Object created_at;
            private Object updated_at;
            private Object city_id;
            private Object state_id;
            private Object country_id;
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

            public Object getCurrent_team_id() {
                return current_team_id;
            }

            public void setCurrent_team_id(Object current_team_id) {
                this.current_team_id = current_team_id;
            }

            public Object getStripe_id() {
                return stripe_id;
            }

            public void setStripe_id(Object stripe_id) {
                this.stripe_id = stripe_id;
            }

            public Object getCurrent_billing_plan() {
                return current_billing_plan;
            }

            public void setCurrent_billing_plan(Object current_billing_plan) {
                this.current_billing_plan = current_billing_plan;
            }

            public Object getBilling_state() {
                return billing_state;
            }

            public void setBilling_state(Object billing_state) {
                this.billing_state = billing_state;
            }

            public Object getVat_id() {
                return vat_id;
            }

            public void setVat_id(Object vat_id) {
                this.vat_id = vat_id;
            }

            public Object getTrial_ends_at() {
                return trial_ends_at;
            }

            public void setTrial_ends_at(Object trial_ends_at) {
                this.trial_ends_at = trial_ends_at;
            }

            public Object getLast_read_announcements_at() {
                return last_read_announcements_at;
            }

            public void setLast_read_announcements_at(Object last_read_announcements_at) {
                this.last_read_announcements_at = last_read_announcements_at;
            }

            public Object getCreated_at() {
                return created_at;
            }

            public void setCreated_at(Object created_at) {
                this.created_at = created_at;
            }

            public Object getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(Object updated_at) {
                this.updated_at = updated_at;
            }

            public Object getCity_id() {
                return city_id;
            }

            public void setCity_id(Object city_id) {
                this.city_id = city_id;
            }

            public Object getState_id() {
                return state_id;
            }

            public void setState_id(Object state_id) {
                this.state_id = state_id;
            }

            public Object getCountry_id() {
                return country_id;
            }

            public void setCountry_id(Object country_id) {
                this.country_id = country_id;
            }

            public int getTax_rate() {
                return tax_rate;
            }

            public void setTax_rate(int tax_rate) {
                this.tax_rate = tax_rate;
            }
        }
    }
}
