import ActivityKit
import WidgetKit
import SwiftUI

public struct FocusTimerAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        var remainingTime: Int
        var totalTime: Int
        var isActive: Bool
        var sessionType: String
    }
    
    var startTime: Date
    var sessionName: String
}

struct YukoPomodoroLiveActivityLiveActivity: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: FocusTimerAttributes.self) { context in
            FocusTimerLockScreenView(context: context)
        } dynamicIsland: { context in
            DynamicIsland {
                DynamicIslandExpandedRegion(.leading) {
                    HStack {
                        Image("icon")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(width: 32, height: 32)
                            .clipShape(Circle())
                        Text(context.state.sessionType)
                            .font(.caption)
                            .fontWeight(.semibold)
                    }
                }
                DynamicIslandExpandedRegion(.trailing) {
                    HStack {
                        Text(formatTime(context.state.remainingTime))
                            .font(.title2)
                            .fontWeight(.bold)
                            .monospacedDigit()
                        
                        if !context.state.isActive {
                            Image(systemName: "pause.circle.fill")
                                .foregroundColor(.orange)
                        }
                    }
                }
                
                DynamicIslandExpandedRegion(.center) {
                    ProgressView(
                        value: Double(context.state.totalTime - context.state.remainingTime),
                        total: Double(context.state.totalTime)
                    )
                    .progressViewStyle(.linear)
                    .tint(context.state.sessionType == "Focus" ? .red : .green)
                }
                
                DynamicIslandExpandedRegion(.bottom) {
                    HStack {
                        Text("Started at \(context.attributes.startTime.formatted(date: .omitted, time: .shortened))")
                            .font(.caption2)
                            .foregroundColor(.secondary)
                        
                        Spacer()
                        
                        Text("\(Int((Double(context.state.totalTime - context.state.remainingTime) / Double(context.state.totalTime)) * 100))% complete")
                            .font(.caption2)
                            .foregroundColor(.secondary)
                    }
                }
                
            } compactLeading: {
                Image("icon")
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 32, height: 32)
                    .clipShape(Circle())
            } compactTrailing: {
                Text(formatTime(context.state.remainingTime))
                    .font(.caption2)
                    .fontWeight(.bold)
                    .monospacedDigit()
            } minimal: {
                Image("icon")
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 32, height: 32)
                    .clipShape(Circle())
            }
            .widgetURL(URL(string: "http://www.apple.com"))
            .keylineTint(Color.red)
        }
    }
}

struct FocusTimerLockScreenView: View {
    let context: ActivityViewContext<FocusTimerAttributes>
    @State private var now = Date()
    
    var body: some View {
        VStack(spacing: 8) {
            let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
            let elapsed = now.timeIntervalSince(context.attributes.startTime)
            let remaining = max(context.state.totalTime - Int(elapsed), 0)
            
            HStack {
                
                HStack(spacing: 4) {
                    Text(context.state.sessionType + "ing...")
                        .font(.caption)
                        .fontWeight(.medium)
                }
                
                Spacer()
                
                if !context.state.isActive {
                    HStack(spacing: 2) {
                        Image(systemName: "pause.circle.fill")
                            .foregroundColor(.orange)
                        Text("Paused")
                            .font(.caption2)
                            .foregroundColor(.orange)
                    }
                }
            }
            
            HStack {
                
                Text(formatTime(remaining))
                    .font(.title)
                    .fontWeight(.bold)
                    .monospacedDigit()
                
                Spacer()
                
                Button(intent: QuitTimerIntent()) {
                    Text("Quit")
                        .font(.caption)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 6)
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(12)
                }
                .buttonStyle(.plain)
            }
            
            ProgressView(
                value: Double(context.state.totalTime - remaining),
                total: Double(context.state.totalTime)
            )
            .progressViewStyle(.linear)
            .tint(context.state.sessionType == "Focus" ? .red : .green)
            .scaleEffect(y: 2)
            
            HStack {
                Text("Started: \(context.attributes.startTime.formatted(date: .omitted, time: .shortened))")
                    .font(.caption2)
                    .foregroundColor(.secondary)
                
                Spacer()
                
                let progressPercent = Int((Double(context.state.totalTime - context.state.remainingTime) / Double(context.state.totalTime)) * 100)
                Text("\(progressPercent)% complete")
                    .font(.caption2)
                    .foregroundColor(.secondary)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
        .background(Color.black.opacity(0.1))
        .cornerRadius(16)
    }
}


private func formatTime(_ seconds: Int) -> String {
    let minutes = seconds / 60
    let remainingSeconds = seconds % 60
    return String(format: "%02d:%02d", minutes, remainingSeconds)
}


extension FocusTimerAttributes {
    fileprivate static var preview: FocusTimerAttributes {
        FocusTimerAttributes(startTime: Date(), sessionName: "Focus")
    }
}

extension FocusTimerAttributes.ContentState {
    
    fileprivate static var content: FocusTimerAttributes.ContentState {
        FocusTimerAttributes.ContentState(
            remainingTime: 300,
            totalTime: 600,
            isActive: true,
            sessionType: "Focus"
        )
    }
}

#Preview("Lock Screen",as: .content, using: FocusTimerAttributes.preview) {
    YukoPomodoroLiveActivityLiveActivity()
} contentStates: {
    FocusTimerAttributes.ContentState.content
}

#Preview("minimal",as: .dynamicIsland(.minimal), using: FocusTimerAttributes.preview) {
    YukoPomodoroLiveActivityLiveActivity()
} contentStates: {
    FocusTimerAttributes.ContentState.content
}

#Preview("Compat",as: .dynamicIsland(.compact), using: FocusTimerAttributes.preview) {
    YukoPomodoroLiveActivityLiveActivity()
} contentStates: {
    FocusTimerAttributes.ContentState.content
}

#Preview("expanded",as: .dynamicIsland(.expanded), using: FocusTimerAttributes.preview) {
    YukoPomodoroLiveActivityLiveActivity()
} contentStates: {
    FocusTimerAttributes.ContentState.content
}

import AppIntents

struct QuitTimerIntent: AppIntent {
    static var title: LocalizedStringResource = "Quit Timer"
    static var description = IntentDescription("Quit the current focus timer session")
    
    func perform() async throws -> some IntentResult {
        UserDefaults(suiteName: "org.kakapo.project.Yuko")?.set(true, forKey: "shouldQuitTimer")
        
        return .result()
    }
}
