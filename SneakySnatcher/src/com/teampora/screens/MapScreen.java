package com.teampora.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.adventure.MapSelection;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.game.State;
import com.teampora.groups.IndicatorBox;
import com.teampora.groups.MapBox;
import com.teampora.managers.SettingsManager;
import com.teampora.scrollpanes.SneakyScrollPane;
import com.teampora.scrollpanes.SneakyScrollPane.Indicator;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class MapScreen extends AbstractScreen implements Indicator {

	private Table background;

	private Table additional_bg;

	private SneakyImageButton back_button;

	private SneakyScrollPane pageScroll;

	private IndicatorBox indicatorBox;

	public MapScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
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
		NinePatchDrawable drawable = null;
		Image img = null;

		final List<MapSelection> maps = game.screen.profile.getMaps();
		/*
		 * disable choosen feature
		 */
		for (MapSelection map : maps) {
			map.setChoosen(false);
		}
		int mapCount = maps.size();
		indicatorBox = new IndicatorBox(mapCount, 0, Assets.selected,
				Assets.unselected);
		MapBox mapBox = null;
		for (int i = 0; i < mapCount; i++) {
			Table t = new Table().pad(50, 150, 50, 150);
			drawable = new NinePatchDrawable(Assets.orange_gradient);
			mapBox = new MapBox(maps.get(i), drawable);
			final int index = i;
			mapBox.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					maps.get(index).setChoosen(true);
					Gdx.app.log("INDEX", index + "");
					game.setScreen(game.screen.getNextScreen(State.LEVEL));
					super.clicked(event, x, y);
				}

			});
			t.add(mapBox);
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
		// stage.addActor(pageScroll);
		stage.addActor(back_button);
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		pageScroll.clear();
		additional_bg.clear();
		background.clear();
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

	}

	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		game.setScreen(game.screen.getNextScreen(State.MENU));
	}
}
