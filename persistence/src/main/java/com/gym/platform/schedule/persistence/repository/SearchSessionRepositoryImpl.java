package com.gym.platform.schedule.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.persistence.model.SessionEntity;

@Repository
public class SearchSessionRepositoryImpl implements SearchSessionRepository {
    // private static final RowMapper<SessionEntity> ROW_MAPPER =
    // DataClassRowMapper.newInstance(SessionEntity.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<SessionEntity> sessionRowMapper;

    @SuppressWarnings("unchecked")
    public SearchSessionRepositoryImpl(
            NamedParameterJdbcTemplate jdbcTemplate,
            JdbcConverter jdbcConverter) {
        this.jdbcTemplate = jdbcTemplate;

        RelationalPersistentEntity<SessionEntity> entity = (RelationalPersistentEntity<SessionEntity>) jdbcConverter
                .getMappingContext()
                .getRequiredPersistentEntity(SessionEntity.class);

        this.sessionRowMapper = new EntityRowMapper<>(entity, jdbcConverter);
    }

    @Override
    public Page<SessionEntity> searchSessions(SessionFilters filters) {
        StringBuilder whereClause = new StringBuilder(" WHERE 1=1 ");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filters.coachIds() != null && !filters.coachIds().isEmpty()) {
            whereClause.append(" AND s.coach_id IN (:coachIds) ");
            params.addValue("coachIds", filters.coachIds());
        }

        if (filters.startTime() != null) {
            if (filters.startTime().from() != null) {
                whereClause.append(" AND s.start_time >= :fromTime ");
                params.addValue("fromTime", Timestamp.from(filters.startTime().from()));
            }
            if (filters.startTime().to() != null) {
                whereClause.append(" AND s.start_time <= :toTime ");
                params.addValue("toTime", Timestamp.from(filters.startTime().to()));
            }
        }

        String countSql = "SELECT COUNT(*) FROM sessions s" + whereClause;
        Long totalElements = jdbcTemplate.queryForObject(countSql, params, Long.class);
        if (totalElements == null || totalElements == 0L) {
            return new Page<>(List.of(), filters.page(), 0L);
        }

        String dataSql = """
                SELECT s.id, s.coach_id, s.location, s.zone_offset, s.start_time, s.end_time, s.capacity
                FROM sessions s
                """ + whereClause + """
                ORDER BY s.start_time ASC, s.id ASC
                LIMIT :limit OFFSET :offset
                """;

        params.addValue("limit", filters.page().size());
        params.addValue("offset", filters.page().offset());

        return new Page<>(jdbcTemplate.query(dataSql, params, sessionRowMapper), filters.page(), totalElements);
    }
}
