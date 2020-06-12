package com.example.cinemhub.models;
/**
 * Classe CastApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class CrewApiTmdbResponse {
    private String credit_id;
    private String department;
    private int gender;
    private int id;
    private String job;
    private String name;
    private String profile_path;

    /**
     * costruttore della classe CrewApiTmdbResponse
     * @param credit_id
     * @param department stringa che identifica il dipartimento del quale fa parte il membro della Crew
     * @param gender intero che indica il sesso del membro della Crew
     * @param id intero che indica univocamente l'ID del membro della Crew
     * @param job stringaa che indica il lavore del membro della Crew
     * @param name stringa indica il nome del membro della Crew
     * @param profile_path stringa per visualizzare il poster della persona
     */
    public CrewApiTmdbResponse(String credit_id, String department, int gender, int id, String job, String name, String profile_path) {
        this.credit_id = credit_id;
        this.department = department;
        this.gender = gender;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profile_path = profile_path;
    }

    /**
     * Costruttore della classe CrewApiTmdbResponse che prende in input un oggetto CrewApiTmdbResponse
     * @param crewApiTmdbResponse oggetto CrewApiTmdbResponse
     */
    public CrewApiTmdbResponse(CrewApiTmdbResponse crewApiTmdbResponse) {
        this.credit_id = crewApiTmdbResponse.getCredit_id();
        this.department = crewApiTmdbResponse.getDepartment();
        this.gender = crewApiTmdbResponse.getGender();
        this.id = crewApiTmdbResponse.getId();
        this.job = crewApiTmdbResponse.getJob();
        this.name = crewApiTmdbResponse.getName();
        this.profile_path = crewApiTmdbResponse.getProfile_path();
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
