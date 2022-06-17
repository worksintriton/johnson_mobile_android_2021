//package com.triton.johnsonapp;
//
//
//import java.util.List;
//
//public class JobnoFetchResponse {
//
//
//    /**
//     * Status : Success
//     * Message : Prescription detail
//     * Data : {"doctorname":"kumar","doctor_speci":[{"specialization":"Radiology"},{"specialization":"Laboratory Animal Medicine"},{"specialization":"Dentistry"},{"specialization":"Behavior"},{"specialization":"Radiation Oncology"},{"specialization":"Nephrology"},{"specialization":"Orthopaedics"},{"specialization":"Emergency"},{"specialization":"Neurology"},{"specialization":"Bone and Joint Specialist"},{"specialization":"Anesthesia & Analgesia"},{"specialization":"Behavioral Medicine"},{"specialization":"Avian & Exotic pets"},{"specialization":"Animal Welfare"},{"specialization":"Opthalmology"},{"specialization":"Preventive Medicine"},{"specialization":"Beef Cattle Practice"},{"specialization":"Dairy Practice"},{"specialization":"Exotic Companion mammal Practice"},{"specialization":"Reptile & Amphibian practice"},{"specialization":"Swine Health Management"},{"specialization":"Zoological"},{"specialization":"neurology1"}],"web_name":"www.petfolio.in","phone_number":"+91-9988776655","app_logo":"http://54.212.108.156:3000/api/uploads/logo.png","owner_name":"Late","user_id":"","name":"mufaza","gender":"","relation_type":"Select Relation Type","health_issue":["Diabetes"],"dateofbirth":"02-03-2022","anymedicalinfo":"gya","covide_vac":"Yes","weight":"50","diagnosis":"Chronic Conditions","sub_diagnosis":"Arbuda (earciñoma)","allergies":"","Doctor_Comments":"dybo","digital_sign":"","Prescription_data":[{"Quantity":"5","Tablet_name":"d hhhhf hi ki","consumption":{"evening":true,"morning":true,"night":true},"intakeBean":{"afterfood":true,"beforefood":true}}],"_id":"622ee8111ab9f7422239c947","doctor_id":"01","Appointment_ID":"622ee7891ab9f7422239c886","Treatment_Done_by":"","Prescription_type":"PDF","PDF_format":"","Prescription_img":"","Date":"14/03/2022 12:30 PM","delete_status":false,"updatedAt":"2022-03-14T07:00:33.431Z","createdAt":"2022-03-14T07:00:33.431Z","health_issue_title":"Vaccination","__v":0,"clinic_no":"01","clinic_loc":"Chennai 2a, 3rd Main Rd, Ram Nagar, Karnam Street, Dhadeswaram Nagar, Velachery, Chennai, Tamil Nadu 600042, India","clinic_name":"saro clinic","Prescription_id":"PRE-1647241233430","share_msg":"Please find the Prescription for the appointment: . You can download Petfolio Mobile App using this link below. http://petfolio.in"}
//     * Code : 200
//     *
//     *
//     * "Status": "Success",
//     *     "Message": "Data Submitted Successfully",
//     *     "Data": {},
//     *     "Code": 200
//     */
//
//    private String Status;
//    private String Message;
//    /**
//     * doctorname : kumar
//     * doctor_speci : [{"specialization":"Radiology"},{"specialization":"Laboratory Animal Medicine"},{"specialization":"Dentistry"},{"specialization":"Behavior"},{"specialization":"Radiation Oncology"},{"specialization":"Nephrology"},{"specialization":"Orthopaedics"},{"specialization":"Emergency"},{"specialization":"Neurology"},{"specialization":"Bone and Joint Specialist"},{"specialization":"Anesthesia & Analgesia"},{"specialization":"Behavioral Medicine"},{"specialization":"Avian & Exotic pets"},{"specialization":"Animal Welfare"},{"specialization":"Opthalmology"},{"specialization":"Preventive Medicine"},{"specialization":"Beef Cattle Practice"},{"specialization":"Dairy Practice"},{"specialization":"Exotic Companion mammal Practice"},{"specialization":"Reptile & Amphibian practice"},{"specialization":"Swine Health Management"},{"specialization":"Zoological"},{"specialization":"neurology1"}]
//     * web_name : www.petfolio.in
//     * phone_number : +91-9988776655
//     * app_logo : http://54.212.108.156:3000/api/uploads/logo.png
//     * owner_name : Late
//     * user_id :
//     * name : mufaza
//     * gender :
//     * relation_type : Select Relation Type
//     * health_issue : ["Diabetes"]
//     * dateofbirth : 02-03-2022
//     * anymedicalinfo : gya
//     * covide_vac : Yes
//     * weight : 50
//     * diagnosis : Chronic Conditions
//     * sub_diagnosis : Arbuda (earciñoma)
//     * allergies :
//     * Doctor_Comments : dybo
//     * digital_sign :
//     * Prescription_data : [{"Quantity":"5","Tablet_name":"d hhhhf hi ki","consumption":{"evening":true,"morning":true,"night":true},"intakeBean":{"afterfood":true,"beforefood":true}}]
//     * _id : 622ee8111ab9f7422239c947
//     * doctor_id : 01
//     * Appointment_ID : 622ee7891ab9f7422239c886
//     * Treatment_Done_by :
//     * Prescription_type : PDF
//     * PDF_format :
//     * Prescription_img :
//     * Date : 14/03/2022 12:30 PM
//     * delete_status : false
//     * updatedAt : 2022-03-14T07:00:33.431Z
//     * createdAt : 2022-03-14T07:00:33.431Z
//     * health_issue_title : Vaccination
//     * __v : 0
//     * clinic_no : 01
//     * clinic_loc : Chennai 2a, 3rd Main Rd, Ram Nagar, Karnam Street, Dhadeswaram Nagar, Velachery, Chennai, Tamil Nadu 600042, India
//     * clinic_name : saro clinic
//     * Prescription_id : PRE-1647241233430
//     * share_msg : Please find the Prescription for the appointment: . You can download Petfolio Mobile App using this link below. http://petfolio.in
//
//
//     * collection_type : Chq
//     * current_date : 23-10-2022
//     * uploaded_file : [{"image":"http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png","image":"http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png","image":"http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png","image":"http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"}]
//     * Agent_code : AG-0001
//     * cheq_no : UI000999
//     * rtgs_no : ""
//     * cheq_amount : 100
//     * cheq_date : 23-May-2022
//     * bank_name : Indian bank
//     * ifsc_code : ""
//     * third_party_chq : Y
//     * ded_it : 1999
//     * ded_gst : 200
//     * ded_other_one_type : Type 1
//     * ded_other_one_value : 100
//     * ded_other_two_type : Type 2
//     * ded_other_two_value : 1000
//     * remarks : asdfasdf
//     * created_by : 98877654321
//     * job_details : [ {"s_no":1,"job_no":"L-0011","customer_name":"Moahmmed","contract_no":"098654321","pay_type":"ADVANCE","frm":"23-May-2022","to":"23-May-2022","pay_amount":10000},
//     *                 {"s_no":2,"job_no":"L-0011","customer_name":"Moahmmed","contract_no":"098654321","pay_type":"ADVANCE","frm":"23-May-2022","to":"23-May-2022","pay_amount":10000},
//     *                 {"s_no":3,"job_no":"L-0011","customer_name":"Moahmmed","contract_no":"098654321","pay_type":"ADVANCE","frm":"23-May-2022","to":"23-May-2022","pay_amount":10000}]
//     */
//
//
//    private DataBean Data;
//    private int Code;
//
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String Status) {
//        this.Status = Status;
//    }
//
//    public String getMessage() {
//        return Message;
//    }
//
//    public void setMessage(String Message) {
//        this.Message = Message;
//    }
//
//    public DataBean getData() {
//        return Data;
//    }
//
//    public void setData(DataBean Data) {
//        this.Data = Data;
//    }
//
//    public int getCode() {
//        return Code;
//    }
//
//    public void setCode(int Code) {
//        this.Code = Code;
//    }
//
//    public static class DataBean {
//        private String collection_type;
//        private String current_date;
//        private String Agent_code;
//        private String cheq_no;
//        private String rtgs_no;
//        private String cheq_amount;
//        private String cheq_date;
//        private String bank_name;
//        private String ifsc_code;
//        private String third_party_chq;
//        private String ded_it;
//        private String ded_gst;
//        private String ded_other_one_type;
//        private String ded_other_one_value;
//        private String ded_other_two_type;
//        private String ded_other_two_value;
//        private String remarks;
//        private String created_by;
//
//        private List<String> health_issue;
//        /**
//         * Quantity : 5
//         * Tablet_name : d hhhhf hi ki
//         */
//
//        private List<PrescriptionDataBean> Prescription_data;
//
//        public String getDoctorname() {
//            return collection_type;
//        }
//
//        public void setDoctorname(String collection_type) {
//            this.collection_type = collection_type;
//        }
//
//        public String getWeb_name() {
//            return current_date;
//        }
//
//        public void setWeb_name(String current_date) {
//            this.current_date = current_date;
//        }
//
//        public String getPhone_number() {
//            return Agent_code;
//        }
//
//        public void setPhone_number(String Agent_code) {
//            this.Agent_code = Agent_code;
//        }
//
//        public String getApp_logo() {
//            return cheq_no;
//        }
//
//        public void setApp_logo(String cheq_no) {
//            this.cheq_no = cheq_no;
//        }
//
//        public String getOwner_name() {
//            return rtgs_no;
//        }
//
//        public void setOwner_name(String rtgs_no) {
//            this.rtgs_no = rtgs_no;
//        }
//
//        public String getUser_id() {
//            return cheq_amount;
//        }
//
//        public void setUser_id(String cheq_amount) {
//            this.cheq_amount = cheq_amount;
//        }
//
//        public String getName() {
//            return cheq_date;
//        }
//
//        public void setName(String cheq_date) {
//            this.cheq_date = cheq_date;
//        }
//
//        public String getGender() {
//            return bank_name;
//        }
//
//        public void setGender(String bank_name) {
//            this.bank_name = bank_name;
//        }
//
//        public String getRelation_type() {
//            return ifsc_code;
//        }
//
//        public void setRelation_type(String ifsc_code) {
//            this.ifsc_code = ifsc_code;
//        }
//
//        public String getDateofbirth() {
//            return third_party_chq;
//        }
//
//        public void setDateofbirth(String third_party_chq) {
//            this.third_party_chq = third_party_chq;
//        }
//
//        public String getAnymedicalinfo() {
//            return ded_it;
//        }
//
//        public void setAnymedicalinfo(String ded_it) {
//            this.ded_it = ded_it;
//        }
//
//        public String getCovide_vac() {
//            return ded_gst;
//        }
//
//        public void setCovide_vac(String ded_gst) {
//            this.ded_gst = ded_gst;
//        }
//
//        public String getWeight() {
//            return weight;
//        }
//
//        public void setWeight(String weight) {
//            this.weight = weight;
//        }
//
//        public String getDiagnosis() {
//            return diagnosis;
//        }
//
//        public void setDiagnosis(String diagnosis) {
//            this.diagnosis = diagnosis;
//        }
//
//        public String getSub_diagnosis() {
//            return sub_diagnosis;
//        }
//
//        public void setSub_diagnosis(String sub_diagnosis) {
//            this.sub_diagnosis = sub_diagnosis;
//        }
//
//        public String getAllergies() {
//            return allergies;
//        }
//
//        public void setAllergies(String allergies) {
//            this.allergies = allergies;
//        }
//
//        public String getDoctor_Comments() {
//            return Doctor_Comments;
//        }
//
//        public void setDoctor_Comments(String Doctor_Comments) {
//            this.Doctor_Comments = Doctor_Comments;
//        }
//
//        public String getDigital_sign() {
//            return digital_sign;
//        }
//
//        public void setDigital_sign(String digital_sign) {
//            this.digital_sign = digital_sign;
//        }
//
//        public String get_id() {
//            return _id;
//        }
//
//        public void set_id(String _id) {
//            this._id = _id;
//        }
//
//        public String getDoctor_id() {
//            return doctor_id;
//        }
//
//        public void setDoctor_id(String doctor_id) {
//            this.doctor_id = doctor_id;
//        }
//
//        public String getAppointment_ID() {
//            return Appointment_ID;
//        }
//
//        public void setAppointment_ID(String Appointment_ID) {
//            this.Appointment_ID = Appointment_ID;
//        }
//
//        public String getTreatment_Done_by() {
//            return Treatment_Done_by;
//        }
//
//        public void setTreatment_Done_by(String Treatment_Done_by) {
//            this.Treatment_Done_by = Treatment_Done_by;
//        }
//
//        public String getPrescription_type() {
//            return Prescription_type;
//        }
//
//        public void setPrescription_type(String Prescription_type) {
//            this.Prescription_type = Prescription_type;
//        }
//
//        public String getPDF_format() {
//            return PDF_format;
//        }
//
//        public void setPDF_format(String PDF_format) {
//            this.PDF_format = PDF_format;
//        }
//
//        public String getPrescription_img() {
//            return Prescription_img;
//        }
//
//        public void setPrescription_img(String Prescription_img) {
//            this.Prescription_img = Prescription_img;
//        }
//
//        public String getDate() {
//            return Date;
//        }
//
//        public void setDate(String Date) {
//            this.Date = Date;
//        }
//
//        public boolean isDelete_status() {
//            return delete_status;
//        }
//
//        public void setDelete_status(boolean delete_status) {
//            this.delete_status = delete_status;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getHealth_issue_title() {
//            return health_issue_title;
//        }
//
//        public void setHealth_issue_title(String health_issue_title) {
//            this.health_issue_title = health_issue_title;
//        }
//
//        public int get__v() {
//            return __v;
//        }
//
//        public void set__v(int __v) {
//            this.__v = __v;
//        }
//
//        public String getClinic_no() {
//            return clinic_no;
//        }
//
//        public void setClinic_no(String clinic_no) {
//            this.clinic_no = clinic_no;
//        }
//
//        public String getClinic_loc() {
//            return clinic_loc;
//        }
//
//        public void setClinic_loc(String clinic_loc) {
//            this.clinic_loc = clinic_loc;
//        }
//
//        public String getClinic_name() {
//            return clinic_name;
//        }
//
//        public void setClinic_name(String clinic_name) {
//            this.clinic_name = clinic_name;
//        }
//
//        public String getPrescription_id() {
//            return Prescription_id;
//        }
//
//        public void setPrescription_id(String Prescription_id) {
//            this.Prescription_id = Prescription_id;
//        }
//
//        public String getShare_msg() {
//            return share_msg;
//        }
//
//        public void setShare_msg(String share_msg) {
//            this.share_msg = share_msg;
//        }
//
//        public List<DoctorSpeciBean> getDoctor_speci() {
//            return doctor_speci;
//        }
//
//        public void setDoctor_speci(List<DoctorSpeciBean> doctor_speci) {
//            this.doctor_speci = doctor_speci;
//        }
//
//        public List<String> getHealth_issue() {
//            return health_issue;
//        }
//
//        public void setHealth_issue(List<String> health_issue) {
//            this.health_issue = health_issue;
//        }
//
//        public List<PrescriptionDataBean> getPrescription_data() {
//            return Prescription_data;
//        }
//
//        public void setPrescription_data(List<PrescriptionDataBean> Prescription_data) {
//            this.Prescription_data = Prescription_data;
//        }
//
//        public static class DoctorSpeciBean {
//            private String specialization;
//
//            public String getSpecialization() {
//                return specialization;
//            }
//
//            public void setSpecialization(String specialization) {
//                this.specialization = specialization;
//            }
//        }
//
//        public static class PrescriptionDataBean {
//            private String Quantity;
//            private String Tablet_name;
//            /**
//             * evening : true
//             * morning : true
//             * night : true
//             */
//
//            private ConsumptionBean consumption;
//            /**
//             * afterfood : true
//             * beforefood : true
//             */
//
//            private IntakeBeanBean intakeBean;
//
//            public String getQuantity() {
//                return Quantity;
//            }
//
//            public void setQuantity(String Quantity) {
//                this.Quantity = Quantity;
//            }
//
//            public String getTablet_name() {
//                return Tablet_name;
//            }
//
//            public void setTablet_name(String Tablet_name) {
//                this.Tablet_name = Tablet_name;
//            }
//
//            public ConsumptionBean getConsumption() {
//                return consumption;
//            }
//
//            public void setConsumption(ConsumptionBean consumption) {
//                this.consumption = consumption;
//            }
//
//            public IntakeBeanBean getIntakeBean() {
//                return intakeBean;
//            }
//
//            public void setIntakeBean(IntakeBeanBean intakeBean) {
//                this.intakeBean = intakeBean;
//            }
//
//            public static class ConsumptionBean {
//                private boolean evening;
//                private boolean morning;
//                private boolean night;
//
//                public boolean isEvening() {
//                    return evening;
//                }
//
//                public void setEvening(boolean evening) {
//                    this.evening = evening;
//                }
//
//                public boolean isMorning() {
//                    return morning;
//                }
//
//                public void setMorning(boolean morning) {
//                    this.morning = morning;
//                }
//
//                public boolean isNight() {
//                    return night;
//                }
//
//                public void setNight(boolean night) {
//                    this.night = night;
//                }
//            }
//
//            public static class IntakeBeanBean {
//                private boolean afterfood;
//                private boolean beforefood;
//
//                public boolean isAfterfood() {
//                    return afterfood;
//                }
//
//                public void setAfterfood(boolean afterfood) {
//                    this.afterfood = afterfood;
//                }
//
//                public boolean isBeforefood() {
//                    return beforefood;
//                }
//
//                public void setBeforefood(boolean beforefood) {
//                    this.beforefood = beforefood;
//                }
//            }
//        }
//    }
//}
