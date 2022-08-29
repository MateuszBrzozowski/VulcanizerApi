package pl.mbrzozowski.vulcanizer.validation;

import org.jetbrains.annotations.NotNull;
import pl.mbrzozowski.vulcanizer.entity.Services;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;
import pl.mbrzozowski.vulcanizer.enums.WheelType;

import java.util.List;

public class ValidationServices {

    public static void validBeforeUpdate(List<Services> servicesList) {
        checkTiresSwap(servicesList);

        checkWheelSwap(servicesList);

        checkWheelBalance(servicesList);

        checkStraightRim(servicesList);
    }

    private static void checkIsMaxOneElement(@NotNull List<Services> servicesList,
                                             TypeOfServices typeOfServices,
                                             WheelType wheelType) {
        int size = servicesList
                .stream()
                .filter(services -> services.getTypeOfServices() == typeOfServices)
                .filter(services -> services.getName() == null)
                .filter(services -> services.getSizeFrom() == null)
                .filter(services -> services.getSizeTo() == null)
                .filter(services -> services.getWheelType() == wheelType)
                .filter(services -> services.getPrice() != null)
                .filter(services -> services.getTime() != null).toList().size();
        if (size > 1) {
            throw new IllegalArgumentException("To much same elements");
        }
    }

    private static void checkTiresSwap(@NotNull List<Services> servicesList) {
        checkIsMaxOneElement(servicesList, TypeOfServices.TIRES_SWAP, null);
        checkIsMaxOneElement(servicesList, TypeOfServices.TIRES_SWAP, WheelType.ALUMINIUM);
        checkIsMaxOneElement(servicesList, TypeOfServices.TIRES_SWAP, WheelType.STEEL);
    }

    private static void checkWheelSwap(List<Services> servicesList) {
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_SWAP, null);
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_SWAP, WheelType.ALUMINIUM);
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_SWAP, WheelType.STEEL);
    }

    private static void checkWheelBalance(List<Services> servicesList) {
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_BALANCE, null);
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_BALANCE, WheelType.ALUMINIUM);
        checkIsMaxOneElement(servicesList, TypeOfServices.WHEEL_BALANCE, WheelType.STEEL);
    }

    private static void checkStraightRim(List<Services> servicesList) {
        checkIsMaxOneElement(servicesList, TypeOfServices.STRAIGHTENING_RIMS, null);
        checkIsMaxOneElement(servicesList, TypeOfServices.STRAIGHTENING_RIMS, WheelType.ALUMINIUM);
        checkIsMaxOneElement(servicesList, TypeOfServices.STRAIGHTENING_RIMS, WheelType.STEEL);
    }
}
