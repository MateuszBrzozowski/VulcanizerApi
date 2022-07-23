package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoritesRequest {
    private Long id;
    private Long userId;
    private Long businessId;

    public FavoritesRequest(Long userId, Long businessId) {
        this.userId = userId;
        this.businessId = businessId;
    }
}
