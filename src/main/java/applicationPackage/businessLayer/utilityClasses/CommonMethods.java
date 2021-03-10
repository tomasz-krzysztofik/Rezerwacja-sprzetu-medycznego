package applicationPackage.businessLayer.utilityClasses;

import org.modelmapper.ModelMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommonMethods {

    private static ModelMapper modelMapper = new ModelMapper();

    static public <T> ModelAndView showView(String view, String key, T objectDTO) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(view);
        modelAndView.addObject(key, objectDTO);
        return modelAndView;
    }

    static public <T, G> List<T> getAllElements(List<G> list, T objectDTO) {
        List<T> objectsDTO = new ArrayList<>();
        Type listType = objectDTO.getClass();
        for (G element : list) {
            objectsDTO.add(modelMapper.map(element, listType));
        }

        return objectsDTO;
    }

    static public ModelAndView resultView(String view, RedirectAttributes redirectAttributes, int returnMessage) {
        redirectAttributes.addFlashAttribute("status", returnMessage);
        return new ModelAndView(view);
    }
}
