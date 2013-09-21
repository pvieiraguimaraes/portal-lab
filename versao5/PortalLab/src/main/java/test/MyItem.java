package test;

public class MyItem {
        private int id;
        private String name;

        public MyItem(int id, String name) {
                super();
                this.id = id;
                this.name = name;
        }

        public String getName() {
                return name;
        }

        public int getId() {
                return id;
        }
  	public String toString() {
		return id+": "+name;
	}

}
