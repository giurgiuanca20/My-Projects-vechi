package org.example.repository.security;

import org.example.model.security.ERole;
import org.example.model.security.Role;
import org.example.model.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RoleRepositorySQLTest {

    private RoleRepositorySQL roleRepository;

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleRepository = new RoleRepositorySQL(mockConnection);
    }

    @Test
    void createRole_Success() throws SQLException {
        ERole roleType = ERole.ADMINISTRATOR;
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        roleRepository.create(roleType);

        verify(mockStatement, times(1)).setString(1, roleType.toString());
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void findRoleByTitle_Success() throws SQLException {
        ERole roleType = ERole.CUSTOMER;
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("role")).thenReturn(roleType.toString());
        when(mockResultSet.getLong(anyString())).thenReturn(1L);
        when(mockResultSet.getString(anyString())).thenReturn(roleType.toString());
        when(mockConnection.prepareStatement(anyString()).executeQuery()).thenReturn(mockResultSet);

        Role role = roleRepository.findRoleByTitle(roleType);

        assertEquals(roleType.toString(), role.getRole());
    }

    @Test
    void findRolesForUser_Success() throws SQLException {
        long userId = 1L;
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("role")).thenReturn(ERole.CUSTOMER.toString());
        when(mockResultSet.getLong(anyString())).thenReturn(1L);
        when(mockResultSet.getString(anyString())).thenReturn(ERole.CUSTOMER.toString());
        when(mockConnection.prepareStatement(anyString()).executeQuery()).thenReturn(mockResultSet);

        List<Role> roles = roleRepository.findRolesForUser(userId);

        assertEquals(1, roles.size());
        assertEquals(ERole.CUSTOMER.toString(), roles.get(0).getRole());
    }

    @Test
    void addRolesToUser_Success() throws SQLException {
        User user = new User();
        user.setId(1L);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, ERole.ADMINISTRATOR.toString()));
        roles.add(new Role(2L, ERole.ADMINISTRATOR.toString()));

        PreparedStatement mockDeleteStatement = mock(PreparedStatement.class);
        PreparedStatement mockInsertStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockDeleteStatement, mockInsertStatement);

        roleRepository.addRolesToUser(user, roles);

        verify(mockDeleteStatement, times(1)).setLong(1, user.getId());
        verify(mockDeleteStatement, times(1)).executeUpdate();
        verify(mockInsertStatement, times(2)).addBatch();
        verify(mockInsertStatement, times(1)).executeBatch();
    }
}