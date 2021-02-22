package com.design.pension_system.sys.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public interface ObjectService {
  int  savePhoto(List<HashMap> photo, String wid,String tableId);

  int deletePhoto(String  mainId,String tableId);

  List<HashMap> queryPhotoByMainId(String  mainId,String tableId );

  Object queryXzqhTree( );

    HashMap<String, Object> selectAll(HashMap<String, Object> params);
}
