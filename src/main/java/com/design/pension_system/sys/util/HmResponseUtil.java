package com.design.pension_system.sys.util;




import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public class HmResponseUtil {
    public HmResponseUtil() {
    }

    public static ResponseEntity<Map> success(Object data) {
        Map result = buildBase(0, "");
        result.put("data", data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Map> success(PageInfo pageInfo) {
        Map page = new HashMap(16);
        page.put("recordsTotal", pageInfo.getTotal());
        page.put("recordsFiltered", pageInfo.getTotal());
        page.put("data", pageInfo.getList());
        return ResponseEntity.ok(page);
    }

    public static ResponseEntity<Map> error(String msg) {
        Map result = buildBase(-1, msg);
        result.put("data", "");
        return ResponseEntity.status(500).body(result);
    }

    public static ResponseEntity<Map> error(int status, String msg) {
        Map result = buildBase(-1, msg);
        result.put("data", "");
        return ResponseEntity.status(status).body(result);
    }

    private static Map<String, Object> buildBase(int errorCode, String msg) {
        Map<String, Object> data = new HashMap(16);
        data.put("errorCode", errorCode);
        data.put("msg", msg);
        return data;
    }

    public class HmResponse {
        private Object data;
        private String msg;
        private int errorCode;

        public HmResponse() {
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getErrorCode() {
            return this.errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
