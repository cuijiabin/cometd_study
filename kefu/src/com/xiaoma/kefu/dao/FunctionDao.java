package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Function;



public interface FunctionDao extends BaseDao<Function>{

	List findBylevel(int level);

	List findTree(int tid);

}
