package avatar.util.misc;

public abstract class Vector<T> {

    protected T x;
    protected T y;
    protected T z;

    public Vector(T one, T two, T three){
        this.x = one;
        this.y = two;
        this.z = three;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public T getZ() {
        return z;
    }

    public abstract Vector<T> copy();
    public abstract Vector<T> add(Vector vector);
    public abstract Vector<T> subtract(Vector vector);

    public static class Vector3D extends Vector<Double>{

        public Vector3D(Double one, Double two, Double three) {
            super(one, two, three);
        }

        @Override
        public Vector<Double> copy() {
            return new Vector3D(x, y, z);
        }

        @Override
        public Vector<Double> add(Vector vector) {
            x += (Double)vector.getX();
            y += (Double)vector.getY();
            z += (Double)vector.getZ();
            return this;
        }

        @Override
        public Vector<Double> subtract(Vector vector) {
            x -= (Double)vector.getX();
            y -= (Double)vector.getY();
            z -= (Double)vector.getZ();
            return this;
        }
    }

    public static class Vector3i extends Vector<Integer>{

        public Vector3i(Integer one, Integer two, Integer three) {
            super(one, two, three);
        }

        @Override
        public Vector<Integer> copy() {
            return new Vector3i(x, y, z);
        }

        @Override
        public Vector<Integer> add(Vector vector) {
            x += (Integer)vector.getX();
            y += (Integer)vector.getY();
            z += (Integer)vector.getZ();
            return this;
        }

        @Override
        public Vector<Integer> subtract(Vector vector) {
            x -= (Integer)vector.getX();
            y -= (Integer)vector.getY();
            z -= (Integer)vector.getZ();
            return this;
        }
    }
}