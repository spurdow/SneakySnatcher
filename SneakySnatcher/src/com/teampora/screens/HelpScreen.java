package com.teampora.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.game.State;
import com.teampora.groups.HelpBox;
import com.teampora.groups.IndicatorBox;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.scrollpanes.SneakyScrollPane;
import com.teampora.scrollpanes.SneakyScrollPane.Indicator;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tables.TableOverlay;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class HelpScreen extends AbstractScreen implements Indicator{
	
	private SneakyImageButton back_button;
	
	private Table background;
	
	private Table additional_bg;
	
	private SneakyScrollPane pageScroll;
	
	private IndicatorBox indicatorBox;

	public HelpScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
		//Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		background = new Table();
		background.setBackground(new TextureRegionDrawable(Assets.background));
		background.setFillParent(true);

		additional_bg = new Table();
		additional_bg.setBackground(new TextureRegionDrawable(
				Assets.additional_background));
		additional_bg.setFillParent(true);
		
		back_button = new SneakyImageButton("Back", Assets.skin,
				Assets.leftArrow_icon);
		back_button.setPosition(0, 0);
		back_button.setSize(Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_NORMAL());
		back_button.addListener(new ClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com
			 * .badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.MENU));
				super.clicked(event, x, y);
			}

		});
		

		pageScroll = new SneakyScrollPane();
		pageScroll.setFlingTime(0.1f);
		pageScroll.setPageSpacing(100);
		pageScroll.setIndicatorListener(this);
		
		indicatorBox = new IndicatorBox(3, 0, Assets.selected,
				Assets.unselected);
		HelpBox helpBox = null;
		for(int i = 0 ; i < 3 ; i++){
			Table t = new Table().pad(50, 150, 50, 150);
			helpBox = new HelpBox(new NinePatchDrawable(Assets.menu_overlay) , i , this);
			t.add(helpBox);
			pageScroll.addPage(t).expandY().fillY();
		}
		
		
		pageScroll.setScrollingDisabled(false, true);
		additional_bg.add(pageScroll).expand().fill();
		additional_bg.row();
		additional_bg.add(indicatorBox)
				.size(stage.getWidth() * 0.4f, Utils.HEIGHT_SMALL() * 0.5f)
				.padBottom(10);
		stage.addActor(background);
		stage.addActor(additional_bg);
		stage.addActor(back_button);
		
		this.show(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, 1).toString() ,Color.GREEN , 1f);
		
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeIndicator(int index) {
		// TODO Auto-generated method stub
		Gdx.app.log("Change Indicator =>", index + "");
		indicatorBox.setCurrent_indicator(index);
		this.show(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, index + 1).toString() ,Color.GREEN , 1f);
		
	}

	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		game.setScreen(game.screen.getNextScreen(State.MENU));
	}
	
	

}
