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

	public void setRegions(Region r) {
		if (this.regions == null) {
			this.regions = new ArrayList<>();
		}
		if(!this.regions.contains(r)){
			this.regions.add(r);
		}
	}

	public void setDelay_circles(String delay_circle, List<Integer> delay_range){
		DelayCirclesItem r = new DelayCirclesItem(delay_circle, delay_range);
		if (this.delay_circles == null) {
			this.delay_circles = new ArrayList<>();
		}
		if (!this.delay_circles.contains(r)) {
			this.delay_circles.add(r);
		}
	}

	public ArrayList<DelayCirclesItem> getDelay_circles(){
		return delay_circles;
	}

	public void setNw_qos_of_regions(String source_region, String destination_region, float latency, float peak_bandwidth, float cost){
		NwQosofRegionsItem r = new NwQosofRegionsItem(source_region, destination_region, latency, peak_bandwidth, cost);
		if (this.nw_qos_of_regions == null) {
			this.nw_qos_of_regions = new ArrayList<>();
		}
		if (!this.nw_qos_of_regions.contains(r)) {
			this.nw_qos_of_regions.add(r);
		}
	}

	public ArrayList<NwQosofRegionsItem> getNw_qos_of_regions(){
		return nw_qos_of_regions;
	}

	public void setGreen_levels(int green_level, List<Integer> carbon_intencity_range){
		GreenLevelsItem r = new GreenLevelsItem(green_level, carbon_intencity_range);
		if (this.green_levels == null) {
			this.green_levels = new ArrayList<>();
		}
		if (!this.green_levels.contains(r)) {
			this.green_levels.add(r);
		}
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

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			GreenLevelsItem r = (GreenLevelsItem) obj;
			return Objects.equals(green_level, r.green_level);
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

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			DelayCirclesItem r = (DelayCirclesItem) obj;
			return Objects.equals(delay_circle, r.delay_circle);
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