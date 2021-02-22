package com.design.pension_system.sys.domain;

public class DicCommon {
    private String id;
    private String name;

    public DicCommon() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", id=").append(this.id);
        sb.append(", name=").append(this.name);
        sb.append("]");
        return sb.toString();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that == null) {
            return false;
        } else if (this.getClass() != that.getClass()) {
            return false;
        } else {
            boolean var10000;
            label48:
            {
                DicCommon other = (DicCommon) that;
                if (this.getId() == null) {
                    if (other.getId() != null) {
                        break label48;
                    }
                } else if (!this.getId().equals(other.getId())) {
                    break label48;
                }

                if (this.getName() == null) {
                    if (other.getName() != null) {
                        break label48;
                    }
                } else if (!this.getName().equals(other.getName())) {
                    break label48;
                }

                var10000 = true;
                return var10000;
            }

            var10000 = false;
            return var10000;
        }
    }

    public int hashCode() {

        int result = 1;
        result = 31 * result + (this.getId() == null ? 0 : this.getId().hashCode());
        result = 31 * result + (this.getName() == null ? 0 : this.getName().hashCode());
        return result;
    }


}
