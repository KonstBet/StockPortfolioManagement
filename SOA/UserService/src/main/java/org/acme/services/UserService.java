package org.acme.services;

import org.acme.domain.Broker;
import org.acme.domain.Investor;
import org.acme.domain.User;
import org.acme.repositories.UserRepository;
import org.acme.resources.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<UserDTO> list(String type) {
        List<UserDTO> userDTOList;

        if (type == null)
            return UserListToUserDTOList(userRepository.findAll().list(), type);
        else if (type.equals("investor"))
            return UserListToUserDTOList(userRepository.findAllInvestors(), type);
        else if (type.equals("broker"))
            return UserListToUserDTOList(userRepository.findAllBrokers(), type);
        else return null;
    }

    public UserDTO get(Integer id) {
        User u = userRepository.findInvestorByID(id);
        if (u != null)
            return new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getPhoneNo(), "investor", ((Investor) u).getCommittedBalance());
        else {
            u = userRepository.findBrokerByID(id);
            if (u != null)
                return new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getPhoneNo(), "broker", ((Broker) u).getBrokerageFee());
        }
        return null;
    }

    private List<UserDTO> UserListToUserDTOList(List<User> userList, String type) {
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        for (User u : userList) {
            UserDTO userDTO;
            if (type == null)
                userDTO = new UserDTO(u.getId(),u.getName(),u.getSurname(),u.getEmail(),u.getPhoneNo(),type);
            else if (type.equals("investor")) {
                userDTO = new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getPhoneNo(), type, ((Investor) u).getCommittedBalance());
            }
            else if (type.equals("broker")) {
                userDTO = new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getPhoneNo(), type, ((Broker) u).getBrokerageFee());
            }
            else return null;//BAD TYPE
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
