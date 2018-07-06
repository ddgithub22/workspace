package com.meganexus.performance.vo;

import java.io.Serializable;

public class StaffHierarrchyVo {
	
	public class Staffhierarchy implements Serializable {
		private static final long serialVersionUID = 1L;
	
		private int id;
		private String cluster;
		private String provider;
		private String sdu;
		private String staff_Name;
		private String team;

		public Staffhierarchy() {
		}

		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCluster() {
			return this.cluster;
		}

		public void setCluster(String cluster) {
			this.cluster = cluster;
		}

		public String getProvider() {
			return this.provider;
		}

		public void setProvider(String provider) {
			this.provider = provider;
		}

		public String getSdu() {
			return this.sdu;
		}

		public void setSdu(String sdu) {
			this.sdu = sdu;
		}

		public String getStaff_Name() {
			return this.staff_Name;
		}

		public void setStaff_Name(String staff_Name) {
			this.staff_Name = staff_Name;
		}

		public String getTeam() {
			return this.team;
		}

		public void setTeam(String team) {
			this.team = team;
		}
	}
}
