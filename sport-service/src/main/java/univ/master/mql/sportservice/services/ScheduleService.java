package univ.master.mql.sportservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.sportservice.entities.Group;
import univ.master.mql.sportservice.entities.Schedule;
import univ.master.mql.sportservice.repository.ScheduleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
	  @Autowired
	  private ScheduleRepository scheduleRep;


	  public void addSchedule(Schedule schedule) {
		  scheduleRep.save(schedule);
	 }
	  public List<Schedule> getSchedules(){
		 List<Schedule> schedules= scheduleRep.findAll();
		 return schedules;
	  }
	  public Optional<Schedule> getSchedule(Long id){
		  return scheduleRep.findById(id);
	  }
	  public void removeScheduleById(Long id){
		   scheduleRep.deleteById(id);
	  }
	  public Schedule editSchedule (Schedule schedule){
		  return scheduleRep.findById(schedule.getId())
				  .map(s -> {
					  if(schedule.getDuration()!="") s.setDuration(schedule.getDuration());
					  if(schedule.getTime()!="")s.setTime(schedule.getTime());
					  if(schedule.getWeekDay()!="")s.setWeekDay(schedule.getWeekDay());
					  s.setEnable(schedule.isEnable());
					  return scheduleRep.save(schedule);
				  })
				  .orElseGet(() -> {
					  return scheduleRep.save(schedule);
				  });
	  }
	  public Optional<Schedule> setGroup(Group group , Long id){
		  return scheduleRep.findById(id)
				  .map(schedule -> {
					  schedule.setGroup(group);
					  return scheduleRep.save(schedule);
				  });
	  }
	  public Optional<Schedule> enable(Long id){
		return scheduleRep.findById(id)
				.map(schedule -> {
					schedule.setEnable(true);
					return scheduleRep.save(schedule);
				});
	}
	  public Optional<Schedule> disable(Long id){
		return scheduleRep.findById(id)
				.map(schedule -> {
					schedule.setEnable(false);
					return scheduleRep.save(schedule);
				});
	}

}
