package util;

/**
 * The {@code Bearing} enum represents the direction an object can be facing.
 */
public enum Bearing {

    NORTH {
        @Override
        public int toInt() {
            return 0;
        }

        @Override
        public String toString() {
            return "North";
        }

    },

    EAST {
        @Override
        public int toInt() {
            return 1;
        }

        @Override
        public String toString() {
            return "East";
        }

    },

    SOUTH {
        @Override
        public int toInt() {
            return 2;
        }

        @Override
        public String toString() {
            return "South";
        }

    },

    WEST {
        @Override
        public int toInt() {
            return 3;
        }

        @Override
        public String toString() {
            return "West";
        }

    };

    /**
     * Transforms this {@code Bearing} to an integer.
     *
     * @return the integer value of this {@code Bearing}
     */
    public abstract int toInt();

    @Override
    public abstract String toString();

}
