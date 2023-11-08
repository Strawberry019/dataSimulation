package com.dataSimulation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class userInfo implements Serializable {
    private String[] groupName;
    private String access_point;
    private int green_level;

    private ArrayList<provision_group> provision_groups;
    private ArrayList<comp_affinity_group> comp_affinity_groups;
    private ArrayList<nw_qos_of_affinity_group> nw_qos_of_affinity_groups;
    private ArrayList<disaster_recovery_policy> disaster_recovery_policies;

    public <T> int calSize(T obj) {
        if (obj == null) {
            return 0;
        } else {
            return ((List<?>) obj).size();
        }
    }

    public void setGroupLNameList(String[] groupName) {
        this.groupName = groupName;
    }

    public String getAccess_point() {
        return access_point;
    }

    public void setAccess_point(String access_point) {
        this.access_point = access_point;
    }

    public int getGreen_level() {
        return green_level;
    }

    public void setGreen_level(int green_level) {
        this.green_level = green_level;
    }

    public ArrayList<provision_group> getProvision_groups() {
        return provision_groups;
    }

    /*
        public int Sizeof_provision_groups() {
            if (provision_groups != null) {
                return provision_groups.size();
            } else {
                return 0;
            }

        }
    */
    public void set_provision_groups(String provision_group_name) {
        provision_group p = new provision_group(provision_group_name);
        if (provision_groups == null) {
            this.provision_groups = new ArrayList<provision_group>();
            this.provision_groups.add(p);
        } else {
            if (!provision_groups.contains(p)) {
                this.provision_groups.add(p);
            }
        }
    }

    public ArrayList<comp_affinity_group> getComp_affinity_groups() {
        return comp_affinity_groups;
    }

    /*public void setComp_affinity_groups(ArrayList<comp_affinity_group> comp_affinity_groups) {

        comp_affinity_group p = new comp_affinity_group();

    }*/

    public ArrayList<nw_qos_of_affinity_group> getNw_qos_of_affinity_groups() {
        return nw_qos_of_affinity_groups;
    }

    public void set_nw_qos_of_affinity_group(String source_group, String destination_group, float latency, float peak_bandwidth) {
        nw_qos_of_affinity_group p = new nw_qos_of_affinity_group(source_group, destination_group, latency, peak_bandwidth);
        if (nw_qos_of_affinity_groups == null) {
            this.nw_qos_of_affinity_groups = new ArrayList<nw_qos_of_affinity_group>();
            this.nw_qos_of_affinity_groups.add(p);
        } else {
            if (!nw_qos_of_affinity_groups.contains(p)) {
                this.nw_qos_of_affinity_groups.add(p);
            }
        }
    }

    public ArrayList<disaster_recovery_policy> getDisaster_recovery_policies() {
        return disaster_recovery_policies;
    }

    /*
        public int Sizeof_disaster_recovery_policies() {
            if (disaster_recovery_policies != null) {
                return disaster_recovery_policies.size();
            } else {
                return 0;
            }

        }
    */
    public void setDisaster_recovery_policies(String dr_range, String affinity_policy, ArrayList<ArrayList<String>> comp_affinity_group) {
        disaster_recovery_policy p = new disaster_recovery_policy(dr_range, affinity_policy, comp_affinity_group);
        if (disaster_recovery_policies == null) {
            this.disaster_recovery_policies = new ArrayList<disaster_recovery_policy>();
            this.disaster_recovery_policies.add(p);
        } else {
            if (!disaster_recovery_policies.contains(p)) {
                this.disaster_recovery_policies.add(p);
            }
        }
    }
}

class comp_affinity_group implements Serializable {
    private String group_name;
    private String delay_circle;
    private ArrayList<resource_sku> resource_skus;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getDelay_circle() {
        return delay_circle;
    }

    public void setDelay_circle(String delay_circle) {
        this.delay_circle = delay_circle;
    }

    public ArrayList<resource_sku> getResource_skus() {
        return resource_skus;
    }

    public void setResource_skus(ArrayList<resource_sku> resource_skus) {
        this.resource_skus = resource_skus;
    }

}

class resource_sku implements Serializable {
    private String sku_name;
    private int amount;
    private ArrayList<az> azs;

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<az> getAzs() {
        return azs;
    }

    public void setAzs(ArrayList<az> azs) {
        this.azs = azs;
    }
}

class az implements Serializable {
    private int az_index;
    private int amount;

    public az(int az_index, int amount) {
        this.az_index = az_index;
        this.amount = amount;
    }

    public int getAz_index() {
        return az_index;
    }

    public void setAz_index(int az_index) {
        this.az_index = az_index;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class nw_qos_of_affinity_group implements Serializable {
    private String source_group;
    private String destination_group;
    private float latency;
    private float peak_bandwidth;

    public nw_qos_of_affinity_group(String source_group, String destination_group, float latency, float peak_bandwidth) {
        this.source_group = source_group;
        this.destination_group = destination_group;
        this.latency = latency;
        this.peak_bandwidth = peak_bandwidth;
    }

    /*public boolean equals(nw_qos_of_affinity_group p1, nw_qos_of_affinity_group p2) {
        return p1.source_group.equals(p2.source_group) && p1.destination_group.equals(p2.destination_group);
    }*/
    public boolean equals(nw_qos_of_affinity_group p) {
        if (p == this) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (this.source_group.equals(p.source_group) && this.destination_group.equals(p.destination_group)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(source_group, destination_group);
    }


    public String getSource_group() {
        return source_group;
    }

    public void setSource_group(String source_group) {
        this.source_group = source_group;
    }

    public String getDestination_group() {
        return destination_group;
    }

    public void setDestination_group(String destination_group) {
        this.destination_group = destination_group;
    }

    public float getLatency() {
        return latency;
    }

    public void setLatency(float latency) {
        this.latency = latency;
    }

    public float getPeak_bandwidth() {
        return peak_bandwidth;
    }

    public void setPeak_bandwidth(float peak_bandwidth) {
        this.peak_bandwidth = peak_bandwidth;
    }
}

class disaster_recovery_policy implements Serializable {
    private String dr_range;
    private String affinity_policy;
    private ArrayList<ArrayList<String>> comp_affinity_group_names;

    public disaster_recovery_policy(String dr_range, String affinity_policy, ArrayList<ArrayList<String>> comp_affinity_group_names) {
        this.dr_range = dr_range;
        this.affinity_policy = affinity_policy;
        this.comp_affinity_group_names = comp_affinity_group_names;
    }

    /*public boolean equals(ArrayList<ArrayList<String>> comp_affinity_group1, ArrayList<ArrayList<String>> comp_affinity_group2) {
        if (comp_affinity_group1.size() == comp_affinity_group2.size()) {
            for (int i = 0; i < comp_affinity_group1.size(); i++) {
                if (!comp_affinity_group1.get(i).equals(comp_affinity_group2.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }*/

    public String getDr_range() {
        return dr_range;
    }

    public void setDr_range(String dr_range) {
        this.dr_range = dr_range;
    }

    public String getAffinity_policy() {
        return affinity_policy;
    }

    public void setAffinity_policy(String affinity_policy) {
        this.affinity_policy = affinity_policy;
    }

    public ArrayList<ArrayList<String>> getComp_affinity_group_names() {
        return comp_affinity_group_names;
    }

    public void setComp_affinity_group_names(ArrayList<ArrayList<String>> comp_affinity_group_names) {
        this.comp_affinity_group_names = comp_affinity_group_names;
    }


}

class provision_group implements Serializable {
    private String name;

    public provision_group(String name) {
        this.name = name;

    }

    /*public boolean equal(provision_group p1, provision_group p2) {
        return p1.name.equals(p2.name);
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(provision_group p) {
        if (p == this) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (this.name.equals(p.name)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(name);
    }
}
