//
//  YukoPomodoroLiveActivityBundle.swift
//  YukoPomodoroLiveActivity
//
//  Created by dwi prasetyo on 01/06/25.
//

import WidgetKit
import SwiftUI

@main
struct YukoPomodoroLiveActivityBundle: WidgetBundle {
    var body: some Widget {
        YukoPomodoroLiveActivity()
        YukoPomodoroLiveActivityControl()
        YukoPomodoroLiveActivityLiveActivity()
    }
}
