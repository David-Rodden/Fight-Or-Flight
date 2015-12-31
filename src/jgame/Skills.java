package jgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skills {
	private List<Skill> skills;

	public Skills() {
		skills = new ArrayList<Skill>();
		skills.addAll(Arrays.asList(new Skill("Combat"), new Skill("Health", 200), new Skill("Endurance")));
	}

	public Skill getCombat() {
		return skills.get(Placement.COMBAT.ordinal());
	}

	public Skill getHealth() {
		return skills.get(Placement.HEALTH.ordinal());
	}

	public Skill getEndurance() {
		return skills.get(Placement.ENDURANCE.ordinal());
	}

	public List<Skill> getAll() {
		return skills;
	}

	private enum Placement {
		COMBAT, HEALTH, ENDURANCE;
	}
}
