package com.emodoki.rest.beans;

public class WitnessInfoResponse {
	private Integer proofId;
	private Integer proofCount;
	private String witnessFbId;
	private String witnessName;

	public Integer getProofId() {
		return proofId;
	}

	public void setProofId(Integer proofId) {
		this.proofId = proofId;
	}

	public Integer getProofCount() {
		return proofCount;
	}

	public void setProofCount(Integer proofCount) {
		this.proofCount = proofCount;
	}

	public String getWitnessFbId() {
		return witnessFbId;
	}

	public void setWitnessFbId(String witnessFbId) {
		this.witnessFbId = witnessFbId;
	}

	public String getWitnessName() {
		return witnessName;
	}

	public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}

}
