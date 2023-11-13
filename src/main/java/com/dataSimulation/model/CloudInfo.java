package com.dataSimulation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CloudInfo implements Serializable {

	private ArrayList<Region> regions;
	private ArrayList<DelayCirclesItem> delay_circles;;
	private ArrayList<NwQosofRegionsItem> nw_qos_of_regions;;
	private ArrayList<GreenLevelsItem> green_levels;;

	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void setRegions(ArrayList<Region> regions) {
		this.regions = regions;
	}

	public void setDelay_circles(ArrayList<DelayCirclesItem> delay_circles){
		this.delay_circles = delay_circles;
	}

	public ArrayList<DelayCirclesItem> getDelay_circles(){
		return delay_circles;
	}

	public void setNw_qos_of_regions(ArrayList<NwQosofRegionsItem> nw_qos_of_regions){
		this.nw_qos_of_regions = nw_qos_of_regions;
	}

	public ArrayList<NwQosofRegionsItem> getNw_qos_of_regions(){
		return nw_qos_of_regions;
	}

	public void setGreen_levels(ArrayList<GreenLevelsItem> green_levels){
		this.green_levels = green_levels;
	}

	public ArrayList<GreenLevelsItem> getGreen_levels(){
		return green_levels;
	}

	class NwQosofRegionsItem implements Serializable {

		private float cost;
		private float peak_bandwidth;
		private float latency;
		private String destination_region;
		private String source_region;

		public NwQosofRegionsItem(String source_region, String destination_region, float latency, float peak_bandwidth,float cost) {
			this.source_region = source_region;
			this.destination_region = destination_region;
			this.latency = latency;
			this.peak_bandwidth = peak_bandwidth;
			this.cost = cost;
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			NwQosofRegionsItem r = (NwQosofRegionsItem) obj;
			return Objects.equals(source_region, r.source_region) && Objects.equals(destination_region, r.destination_region);
		}

		public int hashCode() {
			return Objects.hash(source_region, destination_region);
		}
		public void setCost(float cost){
			this.cost = cost;
		}

		public float getCost(){
			return cost;
		}

		public void setPeak_bandwidth(float peak_bandwidth){
			this.peak_bandwidth = peak_bandwidth;
		}

		public float getPeak_bandwidth(){
			return peak_bandwidth;
		}

		public void setLatency(float latency){
			this.latency = latency;
		}

		public float getLatency(){
			return latency;
		}

		public void setDestination_region(String destination_region){
			this.destination_region = destination_region;
		}

		public String getDestination_region(){
			return destination_region;
		}

		public void setSource_region(String source_region){
			this.source_region = source_region;
		}

		public String getSource_region(){
			return source_region;
		}
	}
	class GreenLevelsItem implements Serializable{

		private List<Integer> carbon_intencity_range;

		private int green_level;

		GreenLevelsItem(int green_level, List<Integer> carbon_intencity_range){
			this.green_level = green_level;
			this.carbon_intencity_range = carbon_intencity_range;
		}

		public void setCarbon_intencity_range(List<Integer> carbon_intencity_range){
			this.carbon_intencity_range = carbon_intencity_range;
		}

		public List<Integer> getCarbon_intencity_range(){
			return carbon_intencity_range;
		}

		public void setGreen_level(int green_level){
			this.green_level = green_level;
		}

		public int getGreen_level(){
			return green_level;
		}
	}
	class DelayCirclesItem implements Serializable{

		private List<Integer> delay_range;

		private String delay_circle;

		public DelayCirclesItem(String delay_circle, List<Integer> delay_range) {
			this.delay_range = delay_range;
			this.delay_circle = delay_circle;
		}

		public void setDelay_range(List<Integer> delay_range){
			this.delay_range = delay_range;
		}

		public List<Integer> getDelay_range(){
			return delay_range;
		}

		public void setDelay_circle(String delay_circle){
			this.delay_circle = delay_circle;
		}

		public String getDelay_circle(){
			return delay_circle;
		}
	}
}