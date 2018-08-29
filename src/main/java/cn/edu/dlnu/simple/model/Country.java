package cn.edu.dlnu.simple.model;

import org.apache.ibatis.type.Alias;

import java.util.Objects;

/**
 * @author     ：xzp.
 * @date       ：Created in 9:33 AM 29/08/2018
 * @Description：这个类是国家类，COUNTRY表对应的Java文件
 */
public class Country {

    private Long id;
    private String countryName;
    private String countryCode;

    public Country() {

    }

    public Country(Long id, String countryName, String countryCode) {
        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(countryName, country.countryName) &&
                Objects.equals(countryCode, country.countryCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, countryName, countryCode);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
