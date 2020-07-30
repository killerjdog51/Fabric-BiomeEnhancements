package io.github.killerjdog51.biome_enhancements.blocks.entities;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import io.github.killerjdog51.biome_enhancements.BiomeEnhancements;
import io.github.killerjdog51.biome_enhancements.blocks.ModStandingSignBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModWallSignBlock;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.SignBlockEntityModel;
import net.minecraft.client.util.Texts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModSignBlockEntityRenderer extends BlockEntityRenderer<ModSignBlockEntity>
{
	 	private static final Identifier OAK_SIGN = new Identifier("textures/entity/signs/oak.png");
	 	private static final Identifier MANGROVE_SIGN = new Identifier(BiomeEnhancements.MOD_ID + ":textures/entity/signs/mangrove.png");
	 	private static final Identifier PALM_SIGN = new Identifier(BiomeEnhancements.MOD_ID + ":textures/entity/signs/palm.png");
	 	private static final Identifier BAOBAB_SIGN = new Identifier(BiomeEnhancements.MOD_ID + ":textures/entity/signs/baobab.png");
	 	
	 	private final SignBlockEntityModel model = new SignBlockEntityModel();

	    public void render(ModSignBlockEntity signBlockEntity, double x, double y, double z, float partialTicks, int destroyStage) {
	       BlockState blockstate = signBlockEntity.getCachedState();
	       GlStateManager.pushMatrix();
	       if (blockstate.getBlock() instanceof ModStandingSignBlock) {
	          GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
	          GlStateManager.rotatef(-((float)(blockstate.get(ModStandingSignBlock.ROTATION) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
	          this.model.getSignpostModel().visible = true;
	       } else {
	          GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
	          GlStateManager.rotatef(-blockstate.get(ModWallSignBlock.FACING).asRotation(), 0.0F, 1.0F, 0.0F);
	          GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);
	          this.model.getSignpostModel().visible = false;
	       }

	       if (destroyStage >= 0) {
	          this.bindTexture(DESTROY_STAGE_TEXTURES[destroyStage]);
	          GlStateManager.matrixMode(5890);
	          GlStateManager.pushMatrix();
	          GlStateManager.scalef(4.0F, 2.0F, 1.0F);
	          GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
	          GlStateManager.matrixMode(5888);
	       } else {
	          this.bindTexture(this.getModelTexture(blockstate.getBlock()));
	       }

	       GlStateManager.enableRescaleNormal();
	       GlStateManager.pushMatrix();
	       GlStateManager.scalef(0.6666667F, -0.6666667F, -0.6666667F);
	       this.model.render();
	       GlStateManager.popMatrix();
	       TextRenderer textRenderer = this.getFontRenderer();
	       GlStateManager.translatef(0.0F, 0.33333334F, 0.046666667F);
	       GlStateManager.scalef(0.010416667F, -0.010416667F, 0.010416667F);
	       GlStateManager.normal3f(0.0F, 0.0F, -0.010416667F);
	       GlStateManager.depthMask(false);
	       int k = signBlockEntity.getTextColor().getSignColor();
	       if (destroyStage < 0) {
	          for(int l = 0; l < 4; ++l) {
	             String string = signBlockEntity.getTextBeingEditedOnRow(l, (text) -> {
	                List<Text> list = Texts.wrapLines(text, 90, textRenderer, false, true);
	                return list.isEmpty() ? "" : ((Text)list.get(0)).asFormattedString();
	             });
	             if (string != null) {
	                textRenderer.draw(string, (float)(-textRenderer.getStringWidth(string) / 2), (float)(l * 10 - signBlockEntity.text.length * 5), k);
	                if (l == signBlockEntity.getCurrentRow() && signBlockEntity.getSelectionStart() >= 0) {
	                   int m = textRenderer.getStringWidth(string.substring(0, Math.max(Math.min(signBlockEntity.getSelectionStart(), string.length()), 0)));
	                   int n = textRenderer.isRightToLeft() ? -1 : 1;
	                   int o = (m - textRenderer.getStringWidth(string) / 2) * n;
	                   int p = l * 10 - signBlockEntity.text.length * 5;
	                   int var10001;
	                   if (signBlockEntity.isCaretVisible()) {
	                      if (signBlockEntity.getSelectionStart() < string.length()) {
	                         var10001 = p - 1;
	                         int var10002 = o + 1;
	                         textRenderer.getClass();
	                         DrawableHelper.fill(o, var10001, var10002, p + 9, -16777216 | k);
	                      } else {
	                         textRenderer.draw("_", (float)o, (float)p, k);
	                      }
	                   }

	                   if (signBlockEntity.getSelectionEnd() != signBlockEntity.getSelectionStart()) {
	                      int q = Math.min(signBlockEntity.getSelectionStart(), signBlockEntity.getSelectionEnd());
	                      int r = Math.max(signBlockEntity.getSelectionStart(), signBlockEntity.getSelectionEnd());
	                      int s = (textRenderer.getStringWidth(string.substring(0, q)) - textRenderer.getStringWidth(string) / 2) * n;
	                      int t = (textRenderer.getStringWidth(string.substring(0, r)) - textRenderer.getStringWidth(string) / 2) * n;
	                      var10001 = Math.min(s, t);
	                      int var10003 = Math.max(s, t);
	                      textRenderer.getClass();
	                      this.getModelPosition(var10001, p, var10003, p + 9);
	                   }
	                }
	             }
	          }
	       }

	       GlStateManager.depthMask(true);
	       GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	       GlStateManager.popMatrix();
	       if (destroyStage >= 0) {
	          GlStateManager.matrixMode(5890);
	          GlStateManager.popMatrix();
	          GlStateManager.matrixMode(5888);
	       }

	    }

	    private Identifier getModelTexture(Block p_217658_1_) {
	       if (p_217658_1_ != ModBlocks.BAOBAB_SIGN && p_217658_1_ != ModBlocks.BAOBAB_WALL_SIGN) {
	          if (p_217658_1_ != ModBlocks.MANGROVE_SIGN && p_217658_1_ != ModBlocks.MANGROVE_WALL_SIGN) {
	                      return p_217658_1_ != ModBlocks.PALM_SIGN && p_217658_1_ != ModBlocks.PALM_WALL_SIGN ? OAK_SIGN : PALM_SIGN;
	                   } else {
	                      return MANGROVE_SIGN;
	                   }
	                } else {
	                   return BAOBAB_SIGN;
	                }
	             } 

	    private void getModelPosition(int p_217657_1_, int p_217657_2_, int p_217657_3_, int p_217657_4_) {
	       Tessellator tessellator = Tessellator.getInstance();
	       BufferBuilder bufferbuilder = tessellator.getBuffer();
	       GlStateManager.color4f(0.0F, 0.0F, 255.0F, 255.0F);
	       GlStateManager.disableTexture();
	       GlStateManager.enableColorLogicOp();
	       GlStateManager.logicOp(GlStateManager.LogicOp.OR_REVERSE);
	       bufferbuilder.begin(7, VertexFormats.POSITION);
	       bufferbuilder.vertex((double)p_217657_1_, (double)p_217657_4_, 0.0D).next();
	       bufferbuilder.vertex((double)p_217657_3_, (double)p_217657_4_, 0.0D).next();
	       bufferbuilder.vertex((double)p_217657_3_, (double)p_217657_2_, 0.0D).next();
	       bufferbuilder.vertex((double)p_217657_1_, (double)p_217657_2_, 0.0D).next();
	       tessellator.draw();
	       GlStateManager.disableColorLogicOp();
	       GlStateManager.enableTexture();
	    }

}
