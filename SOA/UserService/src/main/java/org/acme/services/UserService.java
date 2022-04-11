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
            return new UserDTO(u.getId(), u.getName(), u.getSurname(),null, u.getEmail(), u.getPhoneNo(),u.getAddress(), "investor", ((Investor) u).getCommittedBalance());
        else {
            u = userRepository.findBrokerByID(id);
            if (u != null)
                return new UserDTO(u.getId(), u.getName(), u.getSurname(),null, u.getEmail(), u.getPhoneNo(),u.getAddress(), "broker", ((Broker) u).getBrokerageFee());
        }
        return null;
    }

    public Boolean update(Integer id, UserDTO userDTO) {
        User u = userRepository.findInvestorByID(id);

        if (u != null) {
            Investor i = (Investor) u;
            if (userDTO.getName() != null)
                i.setName(userDTO.getName());
            if (userDTO.getSurname() != null)
                i.setSurname(userDTO.getSurname());
            if (userDTO.getPassword() != null)
                i.setPassword(userDTO.getPassword());
            if (userDTO.getEmail() != null)
                i.setEmail(userDTO.getEmail());
            if (userDTO.getPhoneNo() != null)
                i.setPhoneNo(userDTO.getPhoneNo());
            if (userDTO.getAddress() != null)
                i.setAddress(userDTO.getAddress());

            userRepository.saveUser(i);
            return true;
        }

        u = userRepository.findBrokerByID(id);

        if (u != null) {
            Broker i = (Broker) u;
            if (userDTO.getName() != null)
                i.setName(userDTO.getName());
            if (userDTO.getSurname() != null)
                i.setSurname(userDTO.getSurname());
            if (userDTO.getPassword() != null)
                i.setPassword(userDTO.getPassword());
            if (userDTO.getEmail() != null)
                i.setEmail(userDTO.getEmail());
            if (userDTO.getPhoneNo() != null)
                i.setPhoneNo(userDTO.getPhoneNo());
            if (userDTO.getBrokerageFee() != null)
                i.setBrokerageFee(userDTO.getBrokerageFee());
            if (userDTO.getAddress() != null)
                i.setAddress(userDTO.getAddress());

            userRepository.saveUser(i);
            return true;
        }

        return false;
    }

    public boolean create(UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getSurname() == null || userDTO.getEmail() == null|| userDTO.getPassword() == null)
            return false;

        if (userDTO.getType().equals("investor")) {
            Investor investor = new Investor(userDTO.getName(),userDTO.getSurname(),userDTO.getEmail(),userDTO.getPhoneNo(),userDTO.getPassword());
            investor.setAddress(userDTO.getAddress());

            userRepository.saveUser(investor);
            return true;
        }
        else if (userDTO.getType().equals("broker")) {
            if (userDTO.getBrokerageFee() == null || userDTO.getBrokerageFee() < 1) return false;
            Broker broker = new Broker(userDTO.getName(),userDTO.getSurname(),userDTO.getEmail(),userDTO.getPhoneNo(),userDTO.getPassword(),userDTO.getBrokerageFee());
            broker.setAddress(userDTO.getAddress());

            userRepository.saveUser(broker);
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------

    private List<UserDTO> UserListToUserDTOList(List<User> userList, String type) {
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        for (User u : userList) {
            UserDTO userDTO;
            if (type == null)
                userDTO = new UserDTO(u.getId(),u.getName(),u.getSurname(), null, u.getEmail(),u.getPhoneNo(),u.getAddress(),type);
            else if (type.equals("investor")) {
                userDTO = new UserDTO(u.getId(), u.getName(), u.getSurname(),null, u.getEmail(), u.getPhoneNo(),u.getAddress(), type, ((Investor) u).getCommittedBalance());
            }
            else if (type.equals("broker")) {
                userDTO = new UserDTO(u.getId(), u.getName(), u.getSurname(),null, u.getEmail(), u.getPhoneNo(),u.getAddress(), type, ((Broker) u).getBrokerageFee());
            }
            else return null;//BAD TYPE
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
