package Cardinal.src;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tool {

	// These should be persisted in a properties files / DB, but for exercise Tool types / brands are fixed
	public static enum Type {
		NONE(Constants.EMPTY, new HashSet<Brand>()),
		LADDER("LAD", new HashSet<>(Arrays.asList(Brand.WERNER))),
		CHAINSAW("CHN", new HashSet<>(Arrays.asList(Brand.STIHL))),
		JACKHAMMER("JAK", new HashSet<>(Arrays.asList(Brand.RIDGID, Brand.DEWALT)));

		private String sCode;
		private Set<Brand> availableBrands;

		Type(final String sCode, final Set<Brand> brands) {
			this.sCode = sCode;
			this.availableBrands = brands;
		}

		public String Code() {
			return sCode;
		}

		public boolean hasBrand(final Brand brand) {
			return availableBrands.contains(brand);
		}

		public static Type fromString(final String sValue) {
			for (final Type t : Type.values()) {
				if (t.sCode.equals(sValue)) {
					return t;
				}
			}
			return NONE;
		}
	}

	public static enum Brand {
		NONE(Constants.EMPTY, Constants.EMPTY),
		WERNER("W", "Werner"),
		STIHL("S", "Stihl"),
		RIDGID("R", "Ridgid"),
		DEWALT("D", "DeWalt");

		String sCode;
		String sDisplay;

		Brand(final String sCode, final String sDisplay) {
			this.sCode = sCode;
			this.sDisplay = sDisplay;
		}

		public String Code() {
			return sCode;
		}

		public static Brand fromString(final String sValue) {
			for (final Brand b : Brand.values()) {
				if (b.sCode.equals(sValue)) {
					return b;
				}
			}
			return NONE;
		}
	}

	private Type type = Type.NONE;
	private Brand brand = Brand.NONE;
	private String sCode = Constants.EMPTY;

	public Tool(final Type type, final Brand brand) {
		this.type = type;
		this.brand = brand;
		this.sCode = type.Code() + brand.Code();
	}

	public Type getType() {
		return this.type;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public String getCode() {
		return sCode;
	}

}
