package com.dataSimulation.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserInfo implements Serializable {
    private String access_point;
    private int green_level;

    private ArrayList<ProvisionRegionItem> provision_regions;
    private ArrayList<CompAffinityGroup> comp_affinity_groups;
    private ArrayList<NwQosofAffinityGroupItem> nw_qos_of_affinity_groups;
    private ArrayList<DisasterRecoveryPolicyItem> disaster_recovery_policies;

    public <T> int calSize(T obj) {
        if (obj == null) {
            return 0;
        } else {
            return ((List<?>) obj).size();
        }
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

    public ArrayList<ProvisionRegionItem> getProvision_groups() {
        return provision_regions;
    }

    public void set_provision_groups(String provision_group_name) {
        ProvisionRegionItem p = new ProvisionRegionItem(provision_group_name);
        if (provision_regions == null) {
            this.provision_regions = new ArrayList<ProvisionRegionItem>();
        }
        if (!provision_regions.contains(p)) {
            this.provision_regions.add(p);
        }
    }

    public ArrayList<CompAffinityGroup> getComp_affinity_groups() {
        return comp_affinity_groups;
    }

    public void setComp_affinity_groups(CompAffinityGroup p) {
        if (comp_affinity_groups == null){
            this.comp_affinity_groups = new ArrayList<CompAffinityGroup>();
        }
        if(!comp_affinity_groups.contains(p)){
            this.comp_affinity_groups.add(p);
        }
    }

    public ArrayList<NwQosofAffinityGroupItem> getNw_qos_of_affinity_groups() {
        return nw_qos_of_affinity_groups;
    }

    public void set_nw_qos_of_affinity_group(String source_group, String destination_group, float latency, float peak_bandwidth) {
        NwQosofAffinityGroupItem p = new NwQosofAffinityGroupItem(source_group, destination_group, latency, peak_bandwidth);
        if (nw_qos_of_affinity_groups == null) {
            this.nw_qos_of_affinity_groups = new ArrayList<NwQosofAffinityGroupItem>();
        }
        if (!nw_qos_of_affinity_groups.contains(p)) {
            this.nw_qos_of_affinity_groups.add(p);
        }
    }

    public ArrayList<DisasterRecoveryPolicyItem> getDisaster_recovery_policies() {
        return disaster_recovery_policies;
    }

    public void setDisaster_recovery_policies(String dr_range, String affinity_policy, ArrayList<ArrayList<String>> comp_affinity_group) {
        DisasterRecoveryPolicyItem p = new DisasterRecoveryPolicyItem(dr_range, affinity_policy, comp_affinity_group);
        if (disaster_recovery_policies == null) {
            this.disaster_recovery_policies = new ArrayList<DisasterRecoveryPolicyItem>();
        }
        if (!disaster_recovery_policies.contains(p)) {
            this.disaster_recovery_policies.add(p);
        }
    }

    class ProvisionRegionItem implements Serializable {
        private String name;

        public ProvisionRegionItem(String name) {
            this.name = name;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProvisionRegionItem p = (ProvisionRegionItem) obj;
            return Objects.equals(name, p.name);
        }

        public int hashCode() {
            return Objects.hash(name);
        }
    }

    class NwQosofAffinityGroupItem implements Serializable {
        private String source_group;
        private String destination_group;
        private float latency;
        private float peak_bandwidth;

        public NwQosofAffinityGroupItem(String source_group, String destination_group, float latency, float peak_bandwidth) {
            this.source_group = source_group;
            this.destination_group = destination_group;
            this.latency = latency;
            this.peak_bandwidth = peak_bandwidth;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            NwQosofAffinityGroupItem p = (NwQosofAffinityGroupItem) obj;
            return Objects.equals(source_group, p.source_group) && Objects.equals(destination_group, p.destination_group);
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

    class DisasterRecoveryPolicyItem implements Serializable {
        private String dr_range;
        private String affinity_policy;
        private ArrayList<ArrayList<String>> comp_affinity_group_names;

        public DisasterRecoveryPolicyItem(String dr_range, String affinity_policy, ArrayList<ArrayList<String>> comp_affinity_group_names) {
            this.dr_range = dr_range;
            this.affinity_policy = affinity_policy;
            this.comp_affinity_group_names = comp_affinity_group_names;
        }


        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            DisasterRecoveryPolicyItem p = (DisasterRecoveryPolicyItem) obj;
            return Objects.equals(dr_range, p.dr_range) && Objects.equals(affinity_policy, p.affinity_policy) && Objects.equals(comp_affinity_group_names, p.comp_affinity_group_names);
        }

        public int hashCode() {
            return Objects.hash(dr_range, affinity_policy , comp_affinity_group_names);
        }

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


}




