/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.client.models.obj;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJModel;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.function.Function;

public class IEOBJModel extends OBJModel
{
	public IEOBJModel(MaterialLibrary matLib, ResourceLocation modelLocation)
	{
		super(matLib, modelLocation);
	}

	public IEOBJModel(MaterialLibrary matLib, ResourceLocation modelLocation, Object customData)
	{
		super(matLib, modelLocation);
		this.setCustomData(customData);
	}

	@Nullable
	@Override
	public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
	{
		IBakedModel preBaked = super.bake(bakery, spriteGetter, sprite, format);
		return new IESmartObjModel(preBaked, this, sprite.getState(), format,
				IESmartObjModel.getTexturesForOBJModel(preBaked), null, false);//TODO possibly dynamic?
	}

	@Override
	public IUnbakedModel process(ImmutableMap<String, String> customData)
	{
		IEOBJModel ret = new IEOBJModel(this.getMatLib(), getResourceLocation(), getCustomData());
		return ret;
	}

	@Override
	public IUnbakedModel retexture(ImmutableMap<String, String> textures)
	{
		IEOBJModel ret = new IEOBJModel(this.getMatLib().makeLibWithReplacements(textures), getResourceLocation(), getCustomData());
		return ret;
	}

	static Field f_modelLocation;

	public ResourceLocation getResourceLocation()
	{
		try
		{
			if(f_modelLocation==null)
			{
				f_modelLocation = OBJModel.class.getDeclaredField("modelLocation");
				f_modelLocation.setAccessible(true);
			}
			if(f_modelLocation!=null)
				return (ResourceLocation)f_modelLocation.get(this);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	static Field f_customData;

	public Object getCustomData()
	{
		try
		{
			if(f_customData==null)
			{
				f_customData = OBJModel.class.getDeclaredField("customData");
				f_customData.setAccessible(true);
			}
			if(f_customData!=null)
				return f_customData.get(this);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void setCustomData(Object data)
	{
		try
		{
			if(f_customData==null)
			{
				f_customData = OBJModel.class.getDeclaredField("customData");
				f_customData.setAccessible(true);
			}
			if(f_customData!=null)
				f_customData.set(this, data);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}