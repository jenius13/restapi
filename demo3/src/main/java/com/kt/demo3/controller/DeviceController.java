package com.kt.demo3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.demo3.Repository.DeviceRepository;
import com.kt.demo3.domain.Device;
import com.kt.demo3.domain.Resource;
import com.kt.demo3.message.Message;
import com.kt.demo3.message.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {

    @Autowired
    private final DeviceRepository deviceRepository;
    @Autowired
//    private ResourceRepository resourceRepository;


//    @Autowired
//    @Qualifier("postgresJdbcTemplate")
//    private JdbcTemplate postgresTemplate;

    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @GetMapping
    public List<Device> getdevice() {
        List<Device> deviceList = deviceRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();

        return deviceList;
    }

    @PostMapping("/find")
    public ResponseEntity<Message> findDevice(@RequestBody Map map){
        Device device = deviceRepository.findByDeviceFields(map);
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(device == null){
            message.setResponse(StatusEnum.NOT_FOUND.getCode());
        }else{
            message.setResponse(StatusEnum.OK.getCode());
            message.setData(device);
        }
        return new ResponseEntity<>(message,headers, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<Message> modifyResouceValue(@RequestBody Map map){
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        if(!map.containsKey("resource")){
            message.setResponse(StatusEnum.BAD_REQUEST.getCode());
            message.setData("Resource not set");
            return new ResponseEntity<>(message,headers, HttpStatus.BAD_REQUEST);
        }

        Map res = (Map) map.get("resource");

        if(!res.containsKey("code") || !res.containsKey("value")|| !res.containsKey("group")){
            message.setResponse(StatusEnum.BAD_REQUEST.getCode());
            message.setData("one or more resource fields not correct or not set ");
            return new ResponseEntity<>(message,headers, HttpStatus.BAD_REQUEST);
        }
        Object[] result = deviceRepository.modifyDeviceResourceValue(map);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));



        if(result == null){
            message.setResponse(StatusEnum.NOT_FOUND.getCode());
        }else{

            boolean hasChange = Boolean.parseBoolean(result[0].toString());
            Map<String, Object> customMessage = new HashMap<>();
            message.setResponse(StatusEnum.OK.getCode());
            boolean value = Boolean.parseBoolean(((Map)map.get("resource")).get("value").toString());
            if(hasChange){

                customMessage.put("resultCode", 200 );
                customMessage.put("resultMessage", "Changes successfully" );
            }else{

                customMessage.put("resultCode", 203 );
                customMessage.put("resultMessage", "Value is thesame" );
            }
            message.setData(customMessage);
        }
        return new ResponseEntity<>(message,headers, HttpStatus.OK);
    }


    @GetMapping("/{device_seq}")
    public ResponseEntity<Message> getdeviceById(HttpServletRequest request,
                                                 @PathVariable("device_seq") Integer device_seq) {
        Device device = deviceRepository.findById(device_seq);
       // deviceRepository.findByuserId()
        //Set<Resource> resource = resourceRepository.findre(device_seq);
        int device_count = deviceRepository.getCountdevice(device_seq);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setResponse(StatusEnum.OK.getCode());
        //message.setMessage("성공 코드");
        //message.setData("{device:" + device + "}");
        //message.setDevice_count(device_count);
        Map data = new HashMap<String, Object>();

        //data.putAll(device);
        //ObjectMapper m = new ObjectMapper();
        //Map data =  m.convertValue(device, Map.class);
        data.put("device", device);
        data.put("device_count", device_count);
        message.setData(data);


        return new ResponseEntity<>(message,headers, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Message> getdeviceByUserId(@RequestHeader HttpHeaders header) {

        int userId = Integer.parseInt(header.get("user").get(0));
        List<Device> devices = deviceRepository.findByuserId(userId);
        int device_count = devices.size();//deviceRepository.getCountdevice(device_seq);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setResponse(StatusEnum.OK.getCode());

        Map data = new HashMap<String, Object>();

        data.put("devices", devices);
        data.put("device_count", device_count);
        message.setData(data);


        return new ResponseEntity<>(message,headers, HttpStatus.OK);
    }

//    @GetMapping("/test/{user_id}")
//    List<Resource> findByuserId(@PathVariable("user_id") Integer user_id){
//        List<Resource> resources = deviceRepository.findByuserId(user_id);
//        return resources;

//    /}

//
//    @GetMapping("/api")
//
//    public String getData() {
//
//        List<Device> thedevice = deviceRepository.findAll();
//        Map<String, Object> map = new HashMap<String, Object>();
//        String query = " select * from device";
//        try {
//            map = postgresTemplate.queryForMap(query);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "PostgreSQL Data: " + thedevice;
//    }


}
