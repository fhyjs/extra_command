package org.eu.hanana.reimu.mc.extra_cmd.data;

import com.google.gson.Gson;
import com.mojang.brigadier.StringReader;
import dev.architectury.utils.GameInstance;
import net.minecraft.commands.ParserUtils;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.ComponentUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ToastData {
    public String title;
    public String message;
    public String icon;
    public static ToastData fromJson(String json){
        return new Gson().fromJson(json, ToastData.class);
    }
    public String toJson(){
        return new Gson().toJson(this);
    }

    public ToastData setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public ToastData(String title1, String message1, RegistryAccess registryAccess){
        title=title1;
        message=message1;
        try {
            valid(registryAccess);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Component cannot be parsed: "+e.getCause().getMessage());
        }
    }
    public Component getTitle(RegistryAccess registryAccess){
        return ParserUtils.parseJson(registryAccess,new StringReader(title),ComponentSerialization.CODEC);
    }
    public Component getMessage(RegistryAccess registryAccess){
        return ParserUtils.parseJson(registryAccess,new StringReader(message),ComponentSerialization.CODEC);
    }
    public void valid(RegistryAccess registryAccess) throws InvocationTargetException, IllegalAccessException {
        Class<? extends ToastData> aClass = this.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length<1||method.getParameterTypes()[0] != RegistryAccess.class||!method.getName().startsWith("get")) {
                continue;
            }
            method.invoke(this,registryAccess);
        }
    }
}
