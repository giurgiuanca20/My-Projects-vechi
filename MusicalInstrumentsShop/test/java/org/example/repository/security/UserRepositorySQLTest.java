package org.example.repository.security;

import org.example.model.security.ERole;
import org.example.model.security.User;
import org.example.model.security.Report;
import org.example.model.cart.Order;
import org.example.model.validation.Notification;
import org.example.repository.cart.ReportRepositorySQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserRepositorySQLTest {

    private UserRepositorySQL userRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private RoleRepository mockRoleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepositorySQL(mockConnection, mockRoleRepository);
    }

    @Test
    void findByUsernameAndPassword_Success() throws SQLException {
        String username = "testUser@gmail.com";
        String password = "testPassword31@";

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn(password);
        when(mockRoleRepository.findRolesForUser(anyLong())).thenReturn(new ArrayList<>());

        User user = userRepository.findByUsernameAndPassword(username, password).getResult();

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        verify(mockRoleRepository, times(1)).findRolesForUser(anyLong());
    }

    @Test
    void insertUserRole_Success() throws SQLException {
        Long userId = 1L;
        int role = 2;

        PreparedStatement mockStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doNothing().when(mockStatement).setLong(anyInt(), anyLong());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);
        userRepository.insertUserRole(userId, role);

        verify(mockStatement, times(1)).setLong(1, userId);
        verify(mockStatement, times(1)).setInt(2, role);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void findRoleByUsernameAndPassword_Success() throws SQLException {
        String username = "testUser@gmail.com";
        String password = "testPassword31@";

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER");

        ERole role = userRepository.findRoleByUsernameAndPassword(username, password);

        assertEquals(ERole.CUSTOMER, role);
    }
    @Test
    void findByUsernameAndPassword_InvalidCredentials() throws SQLException {
        String username = "testUser@gmail.com";
        String password = "testPassword31@";

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Notification<User> notification = userRepository.findByUsernameAndPassword(username, password);

        assertTrue(notification.hasErrors());

    }

    @Test
    void insertUser_Success() throws SQLException {
        String username = "testUser";
        String password = "testPassword";

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(1L);

        Long userId = userRepository.insertUser(username, password);

        assertEquals(1L, userId);
    }
    @Test
    void createUserForAccount_Success() throws SQLException {
        String username = "testUser";
        String password = "testPassword";

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(1L);

        Long userId = userRepository.createUserForAccount(username, password);

        assertEquals(1L, userId);
    }
    @Test
    void getGuestIds_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getLong("user_id")).thenReturn(1L, 2L);

        List<Long> guestIds = userRepository.getGuestIds();

        assertEquals(2, guestIds.size());
        assertTrue(guestIds.contains(1L));
        assertTrue(guestIds.contains(2L));
    }



}