package org.springframework.samples.petclinic.recoveryroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosProperties.Recovery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecoveryRoomService {
	
	private RecoveryRoomRepository repo;
	
	@Autowired
	public RecoveryRoomService(RecoveryRoomRepository repo) {
		this.repo = repo;
	}
	
    public List<RecoveryRoom> getAll(){
        return repo.findAll();
    }

    public List<RecoveryRoomType> getAllRecoveryRoomTypes(){
        return repo.findAllRecoveryRoomTypes();
    }

    public RecoveryRoomType getRecoveryRoomType(String typeName) {
        return repo.getRecoveryRoomType(typeName);
    }

    @Transactional(rollbackFor = DuplicatedRoomNameException.class)
    public RecoveryRoom save(RecoveryRoom p) throws DuplicatedRoomNameException {
    	RecoveryRoom room = repo.findByName(p.getName());
    	if (room == null) {
    		return repo.save(p);   
    	} else {
    		throw new DuplicatedRoomNameException();
    	}
    }

    
}
