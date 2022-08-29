package pl.mbrzozowski.vulcanizer.validation;

import org.jetbrains.annotations.NotNull;
import pl.mbrzozowski.vulcanizer.entity.Services;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;
import pl.mbrzozowski.vulcanizer.enums.WheelType;

import java.util.List;

public class ValidationServices {

    public static void validBeforeUpdate(List<Services> servicesList) {
        checkPriceTimeTypeIsNotNull(servicesList);
        checkTiresSwap(servicesList);
        checkWheelSwap(servicesList);
        checkWheelBalance(servicesList);
        checkStraightRim(servicesList);
    }

    private static void checkPriceTimeTypeIsNotNull(List<Services> servicesList) {
        int size = servicesList
                .stream()
                .filter(services -> services.getPrice() == null ||
                        services.getTime() == null ||
                        services.getTypeOfServices() == null)
                .toList()
                .size();
        if (size > 0) {
            throw new IllegalArgumentException("Price, Time, Type of Services is required");
        }
    }

    private static void checkElements(@NotNull List<Services> servicesList,
                                      @NotNull TypeOfServices typeOfServices,
                                      WheelType wheelType) {
        int size = servicesList
                .stream()
                .filter(services -> services.getTypeOfServices() == typeOfServices)
                .filter(services -> services.getName() == null)
                .filter(services -> services.getSizeFrom() == null)
                .filter(services -> services.getSizeTo() == null)
                .filter(services -> services.getWheelType() == wheelType)
                .toList()
                .size();
        if (size > 1) {
            throw new IllegalArgumentException("To much same elements");
        }
        if (wheelType != null) {
            List<Services> list = getSizeList(servicesList, typeOfServices, wheelType);
            if (size == 1 && list.size() > 0) {
                checkList(list);
            } else if (size == 0 && list.size() > 0) {
                throw new IllegalArgumentException(String.format("Required default price and time for %s/%s",
                                typeOfServices.name(),
                                wheelType.name()));
            }
        }
    }

    private static void checkTiresSwap(@NotNull List<Services> servicesList) {
        checkElements(servicesList, TypeOfServices.TIRES_SWAP, null);
        checkElements(servicesList, TypeOfServices.TIRES_SWAP, WheelType.ALUMINIUM);
        checkElements(servicesList, TypeOfServices.TIRES_SWAP, WheelType.STEEL);
    }

    private static void checkWheelSwap(List<Services> servicesList) {
        checkElements(servicesList, TypeOfServices.WHEEL_SWAP, null);
        checkElements(servicesList, TypeOfServices.WHEEL_SWAP, WheelType.ALUMINIUM);
        checkElements(servicesList, TypeOfServices.WHEEL_SWAP, WheelType.STEEL);
    }

    private static void checkWheelBalance(List<Services> servicesList) {
        checkElements(servicesList, TypeOfServices.WHEEL_BALANCE, null);
        checkElements(servicesList, TypeOfServices.WHEEL_BALANCE, WheelType.ALUMINIUM);
        checkElements(servicesList, TypeOfServices.WHEEL_BALANCE, WheelType.STEEL);
    }

    private static void checkStraightRim(List<Services> servicesList) {
        checkElements(servicesList, TypeOfServices.STRAIGHTENING_RIMS, null);
        checkElements(servicesList, TypeOfServices.STRAIGHTENING_RIMS, WheelType.ALUMINIUM);
        checkElements(servicesList, TypeOfServices.STRAIGHTENING_RIMS, WheelType.STEEL);
    }

    private static List<Services> getSizeList(@NotNull List<Services> servicesList,
                                              @NotNull TypeOfServices typeOfServices,
                                              @NotNull WheelType wheelType) {
        return servicesList
                .stream()
                .filter(services -> services.getTypeOfServices() == typeOfServices)
                .filter(services -> services.getWheelType() == wheelType)
                .filter(services -> services.getSizeFrom() != null && services.getSizeTo() != null)
                .toList();
    }

    private static void checkList(List<Services> list) {
        if (list.size() > 10) {
            throw new IllegalArgumentException("List of sizing is to large");
        }
        for (Services services : list) {
            if (services.getSizeFrom() > services.getSizeTo()) {
                throw new IllegalArgumentException("Size from is larger than size to");
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getSizeFrom() > list.get(i).getSizeFrom()) {
                    if (list.get(j).getSizeFrom() <= list.get(i).getSizeTo()) {
                        throw new IllegalArgumentException("Sizing is not valid");
                    }
                } else if (list.get(j).getSizeFrom() < list.get(i).getSizeFrom()) {
                    if (list.get(j).getSizeTo() >= list.get(i).getSizeFrom()) {
                        throw new IllegalArgumentException("Sizing is not valid");
                    }
                } else if (list.get(j).getSizeFrom().equals(list.get(i).getSizeFrom())) {
                    throw new IllegalArgumentException("Sizing is not valid");
                }

            }
        }
    }
}
