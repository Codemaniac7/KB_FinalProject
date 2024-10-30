package com.kb.board.handler;

import com.kb.board.dto.BoardStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardStatusTypeHandler extends BaseTypeHandler<BoardStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BoardStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public BoardStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String status = rs.getString(columnName);
        return status != null ? BoardStatus.fromValue(status) : null;
    }

    @Override
    public BoardStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String status = rs.getString(columnIndex);
        return status != null ? BoardStatus.fromValue(status) : null;
    }

    @Override
    public BoardStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String status = cs.getString(columnIndex);
        return status != null ? BoardStatus.fromValue(status) : null;
    }
}
