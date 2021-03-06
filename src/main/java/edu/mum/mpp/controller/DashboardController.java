package edu.mum.mpp.controller;

import edu.mum.mpp.model.Block;
import edu.mum.mpp.model.StockRequest;
import edu.mum.mpp.model.User;
import edu.mum.mpp.service.*;
import edu.mum.mpp.util.AppointmentDataUtil;
import edu.mum.mpp.util.CustomResponseCode;
import edu.mum.mpp.util.HollydayDataUtil;
import edu.mum.mpp.util.SupplierDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
public class DashboardController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    StockService stockService;

    private User loggedInUser;

    @Autowired
    CellService cellService;

    @Autowired
    BlockService blockService;

    @Autowired
    FoodService foodService;

    @Autowired
    AnimalService animalService;

    @Autowired
    MedicineService medicineService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/holidayPackage", method = RequestMethod.GET)
    public ModelAndView holidayPackage() {
        ModelAndView model = new ModelAndView();

        model.addObject("holidays", HollydayDataUtil.displayHollydays());
        model.setViewName("holidayPackage");
        return model;
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }


    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView report() {
        ModelAndView model = new ModelAndView();

        model.addObject("reports", animalService.report(1, 20).getContent());
        model.setViewName("report");
        return model;
    }

    @RequestMapping(value = "/confirmLogin", method = RequestMethod.GET)
    public ModelAndView confirmLogin() {
        ModelAndView model = new ModelAndView();
        String page = "login?emr=You are not authorised to access this page";

        loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        if (loggedInUser != null) {
            if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory().name()))
                page = "dashboard";

        }

        model.setViewName("redirect:" + page);
        return model;
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request) throws IOException {
        ModelAndView model = new ModelAndView();

        //String valueJson = request.getParameter("_a");

        //ObjectMapper objectMapper = new ObjectMapper();

        //User u = objectMapper.readValue(valueJson, User.class);

        String page = "dashboard";

        model.setViewName(page);
        return model;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contact() {
        ModelAndView model = new ModelAndView();
        model.setViewName("contact");
        return model;
    }


    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public ModelAndView gallery() {
        ModelAndView model = new ModelAndView();
        model.setViewName("gallery");
        return model;
    }


    @RequestMapping(value = "/manageBlock", method = RequestMethod.GET)
    public ModelAndView manageBlock() {
        ModelAndView model = new ModelAndView();

        //  String page = "login?emr=You are not authorised to access this page";

        //User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        // if (loggedInUser != null) {

        // if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //   page = "manageBlock";
        // model.addObject("blocks", BlockDataUtil.displayBlocks());

        model.addObject("blocks", blockService.getBlocks(1, 20).getContent());

        // }
        // }

        model.setViewName("manageBlock");
        return model;
    }

    @RequestMapping(value = "/manageFood", method = RequestMethod.GET)
    public ModelAndView manageFood() {
        ModelAndView model = new ModelAndView();

        //   String page = "login?emr=You are not authorised to access this page";


        // User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {

        //  if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //    page = "manageFood";
        model.addObject("foods", foodService.getFoods(1, 20).getContent());
        //}

        //  }

        model.setViewName("manageFood");

        return model;
    }

    @RequestMapping(value = "/manageHollydayPackage", method = RequestMethod.GET)
    public ModelAndView manageHollydayPackage() {
        ModelAndView model = new ModelAndView();

        //  String page = "login?emr=You are not authorised to access this page";
        // User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {
        //  if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //    page = "manageHollydayPackage";
        model.addObject("hollydays", HollydayDataUtil.displayHollydays());
        //}


//        }

        model.setViewName("manageHollydayPackage");

        return model;
    }


    @RequestMapping(value = "/manageStock", method = RequestMethod.GET)
    public ModelAndView manageStock(@ModelAttribute("command") StockRequest stockRequest) {
        ModelAndView model = new ModelAndView();


        //   String page = "login?emr=You are not authorised to access this page";
        // User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {

        //  if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        // page = "manageStock";
        model.addObject("stocks", stockService.displayStockReport());
        model.addObject("suppliers", SupplierDataUtil.getSupplierListForDropDown());
        model.addObject("itemIds", foodService.getFoodListForDropDown());
        // }

        //}

        model.setViewName("manageStock");
        return model;
    }


    @RequestMapping(value = "/manageMedicine", method = RequestMethod.GET)
    public ModelAndView manageMedicine() {
        ModelAndView model = new ModelAndView();

        // String page = "login?emr=You are not authorised to access this page";
        //   User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        // if (loggedInUser != null) {
        //   if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //  page = "manageMedicine";
        model.addObject("medicines", medicineService.getMedicines(1, 20));
        //}

        //}

        model.setViewName("manageMedicine");

        return model;
    }


    @RequestMapping(value = "/manageCell", method = RequestMethod.GET)
    public ModelAndView manageCell(@ModelAttribute("command") Block block) {
        ModelAndView model = new ModelAndView();

        // String page = "login?emr=You are not authorised to access this page";
        //User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {

        //  if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //    page = "manageCell";
        model.addObject("blocks", blockService.getBlockListForDropDown());
        model.addObject("cells", cellService.displayCellReport());
        //}

        //   }

        model.setViewName("manageCell");

        return model;
    }

    @RequestMapping(value = "/manageAppointment", method = RequestMethod.GET)
    public ModelAndView manageAppointment() {
        ModelAndView model = new ModelAndView();


        // String page = "login?emr=You are not authorised to access this page";
        //User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {
        //  if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

//                page = "manageAppointment";
        model.addObject("appointments", AppointmentDataUtil.displayAppointments());
        //          }

        //    }

        model.setViewName("manageAppointment");

        return model;
    }


    @RequestMapping(value = "/manageAnimal", method = RequestMethod.GET)
    public ModelAndView manageAnimal(@ModelAttribute("command") Block block) {

        ModelAndView model = new ModelAndView();


        //String page = "login?emr=You are not authorised to access this page";
        //User loggedInUser = TokenService.getCurrentUserFromSecurityContext();
        //if (loggedInUser != null) {
        // if (CustomResponseCode.USER_CATEGORY_EMPLOYEE.equals(loggedInUser.getCategory())) {

        //   page = "manageAnimal";
        // model.addObject("blocks", BlockDataUtil.getBlockListForDropDown());
        // model.addObject("cells", CellDataUtil.getCellListForDropDown());
        model.addObject("blocks", blockService.getBlockListForDropDown());
        model.addObject("cells", cellService.getCellListForDropDown());
        model.addObject("animals", animalService.getAnimals(1, 20).getContent());
        //}

        //}

        model.setViewName("manageAnimal");

        return model;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }


    @RequestMapping(value = "/loginAdmin", method = RequestMethod.GET)
    public ModelAndView loginAdmin() {
        ModelAndView model = new ModelAndView();
        model.setViewName("loginAdmin");
        return model;
    }


    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public ModelAndView table() {
        ModelAndView model = new ModelAndView();
        model.setViewName("table");
        return model;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView model = new ModelAndView();
        model.setViewName("welcome");
        return model;
    }


}