package com.personal.jello.workout.database;

import android.app.Application;
import android.os.AsyncTask;

import com.personal.jello.workout.dataAccess.WeightTrainingDao;
import com.personal.jello.workout.models.WeightTrainingRecord;

import java.util.List;

public class WorkoutRepository {
    private WeightTrainingDao weightTrainingDao;
    private List<WeightTrainingRecord> records;

    public WorkoutRepository(Application application) {
        WorkoutDatabase db = WorkoutDatabase.getWorkoutDatabase(application);
        weightTrainingDao = db.weightTrainingDao();
        records = weightTrainingDao.getAll();
    }

    public List<WeightTrainingRecord> getAllRecords() {
        return records;
    }

    public void insertRecord(WeightTrainingRecord record) {
        new insertAsyncTask(weightTrainingDao).execute(record);
    }

    private static class insertAsyncTask extends AsyncTask<WeightTrainingRecord, Void, Void> {
        private WeightTrainingDao weightTrainingAsyncTaskDao;

        insertAsyncTask(WeightTrainingDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecord... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
