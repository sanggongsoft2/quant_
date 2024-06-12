package com.quant_socket.repos;

import com.quant_socket.models.Product;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EquitiesSnapshotRepo extends SG_repo<Product>{
}
