public interface MultiSet<T> {
    @Override
    public void add(T item) {
        _tree.insert(item);
    }

    @Override
    public void remove(T item) {
        _tree.delete(item);
    }

    @Override
    public boolean contains(T item) {
        return _tree.contains(item);
    }

    @Override
    public boolean isEmpty() {
        return _tree.isEmpty();
    }

    @Override
    public int count(T item) {
        return _tree.count(item);
    }

    @Override
    public int size() {
        return _tree.getLength();
    }


}
