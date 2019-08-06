package com.nougui.networkstatechanger;

import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NetworkStateTile extends TileService {

    private int preferredState;

    @Override
    public void onTileAdded() {
        preferredState = NetworkChanger.getPreferredNetwork(this);
        updateTile();
    }

    @Override
    public void onStartListening() {
        preferredState = NetworkChanger.getPreferredNetwork(this);
        updateTile();
    }

    @Override
    public void onClick() {
        if (!isSecure()) {

            showDialog();

        } else {

            unlockAndRun(new Runnable() {
                @Override
                public void run() {

                    showDialog();
                }
            });
        }
        preferredState = NetworkChanger.getPreferredNetwork(this);
        updateTile();
    }

    @Override
    public void onStopListening() {
        preferredState = NetworkChanger.getPreferredNetwork(this);
        updateTile();
    }

    @Override
    public void onTileRemoved() {
    }


    private void updateTile() {

        Tile tile = this.getQsTile();

        Icon newIcon;
        String newLabel;
        int newState;

        // Change the tile to match the service status.

        newLabel = NetworkChanger.getPreferredNetworkName(preferredState);

        newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.baseline_import_export_black_18dp);

        newState = Tile.STATE_ACTIVE;

        tile.setLabel(newLabel);
        tile.setIcon(newIcon);
        tile.setState(newState);

        tile.updateTile();
    }


    private void showDialog() {

        showDialog(TileDialog.getDialog(this));

    }


}

