package net.pixaurora.kitten_square.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.ToastGui;
import net.pixaurora.kitten_square.impl.ui.toast.ToastManager;

@Mixin(ToastGui.class)
public class ToastGuiMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void renderOwnToasts(CallbackInfo cInfo) {
        ToastManager.INSTANCE.render();
    }
}
