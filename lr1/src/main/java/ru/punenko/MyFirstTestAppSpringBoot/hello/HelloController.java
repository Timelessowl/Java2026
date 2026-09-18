package ru.punenko.MyFirstTestAppSpringBoot.hello;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private ArrayList<String> arrayList;
    private HashMap<Integer, String> hashMap;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public synchronized String updateArrayList(@RequestParam("s") String s) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(s);
        return "Added to array: " + s;
    }

    @GetMapping("/show-array")
    public synchronized ArrayList<String> showArrayList() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return new ArrayList<>(arrayList);
    }

    @GetMapping("/update-map")
    public synchronized String updateHashMap(@RequestParam("s") String s) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(hashMap.size() + 1, s);
        return "Added to map: " + s;
    }

    @GetMapping("/show-map")
    public synchronized HashMap<Integer, String> showHashMap() {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        return new HashMap<>(hashMap);
    }

    @GetMapping("/show-all-lenght")
    public synchronized String showAllLenght() {
        int arrayCount = arrayList == null ? 0 : arrayList.size();
        int mapCount = hashMap == null ? 0 : hashMap.size();
        return String.format("ArrayList: %d, HashMap: %d", arrayCount, mapCount);
    }
}
